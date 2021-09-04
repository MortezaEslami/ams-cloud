package com.sample.ams.service;

import com.sample.ams.exception.ResourceNotFoundException;
import com.sample.ams.service.Interface.IGenericService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@RequiredArgsConstructor
public abstract class GenericService<T, ID extends Serializable, R, C, U> implements IGenericService<T, ID, R, C, U> {

    private Class<T> tType;
    private Class<R> rType;
    private Class<U> uType;


    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected EntityManager entityManager;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected JpaRepository<T, ID> repository;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected JpaSpecificationExecutor<T> repositorySpecificationExecutor;

    {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        //noinspection unchecked
        tType = (Class<T>) superClass.getActualTypeArguments()[0];
        //noinspection unchecked
        rType = (Class<R>) superClass.getActualTypeArguments()[2];
        //noinspection unchecked
        uType = (Class<U>) superClass.getActualTypeArguments()[4];
    }

    @Override
    @Transactional(readOnly = true)
    public R get(ID id) {
        final Optional<T> entityById = repository.findById(id);
        final T entity = entityById.orElseThrow(() -> new ResourceNotFoundException("No result found for your request id = " + id));
        return modelMapper.map(entity, rType);

    }

    @Override
    @Transactional(readOnly = true)
    public List<R> list() {
        final List<T> entities = repository.findAll();
        return entities.stream().map(this::apply).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public R create(C request) {
        final T entity = modelMapper.map(request, tType);
        return save(entity);
    }

    @Override
    @Transactional
    public R update(ID id, U request) {
        final Optional<T> entityById = repository.findById(id);
        final T entity = entityById.orElseThrow(() -> new ResourceNotFoundException("No result found for your request id = " + id));
        try {
            T updating = tType.getDeclaredConstructor().newInstance();
            modelMapper.map(entity, updating);
            modelMapper.map(request, updating);
            return save(updating);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            log.error("Exception", e);
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(ID id) {
        final Optional<T> entityById = repository.findById(id);
        final T entity = entityById.orElseThrow(() -> new ResourceNotFoundException("No result found for your request id = " + id));
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public R save(T entity) {
        return modelMapper.map(repository.saveAndFlush(entity), rType);
    }

    private R apply(T q) {
        return modelMapper.map(q, rType);
    }
}
