package org.eurekaclinical.registry.service.dao;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import org.eurekaclinical.registry.service.entity.UserTemplateEntity;
import org.eurekaclinical.standardapis.dao.AbstractJpaUserTemplateDao;
import org.eurekaclinical.registry.service.entity.UserTemplateEntity_;

/**
 *
 *
 * @author Dileep Gunda
 */
public class JpaUserTemplateDao extends AbstractJpaUserTemplateDao<UserTemplateEntity> {

	/**
	 * Create an object with the give entity manager.
	 *
	 * @param inEMProvider The entity manager to be used for communication with
	 * the data store.
	 */
	@Inject
	public JpaUserTemplateDao(final Provider<EntityManager> inEMProvider) {
		super(UserTemplateEntity.class, inEMProvider);
	}

	@Override
	public UserTemplateEntity getAutoAuthorizationTemplate() {
		List<UserTemplateEntity> result = this.getListByAttribute(UserTemplateEntity_.autoAuthorize, Boolean.TRUE);
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

}
