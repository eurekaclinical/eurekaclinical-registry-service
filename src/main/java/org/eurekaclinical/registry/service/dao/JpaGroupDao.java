package org.eurekaclinical.registry.service.dao;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.eurekaclinical.registry.service.entity.GroupEntity;
import org.eurekaclinical.standardapis.dao.AbstractJpaGroupDao;


/**
 * 
 *
 * @author Dileep
 */
public class JpaGroupDao extends AbstractJpaGroupDao<GroupEntity> {

    /**
     * Create an object with the give entity manager.
     *
     * @param inEMProvider The entity manager to be used for communication with
     * the data store.
     */
    @Inject
    public JpaGroupDao(final Provider<EntityManager> inEMProvider) {
        super(GroupEntity.class, inEMProvider);
    }

}
