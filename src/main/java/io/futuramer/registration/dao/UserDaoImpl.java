package io.futuramer.registration.dao;

import io.futuramer.registration.model.Appuser_;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.futuramer.registration.model.Appuser;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository("userDao")
@Transactional 
public class UserDaoImpl extends AbstractDao<Integer, Appuser> implements UserDao{
	
    public Appuser findById(int id) {
		return getByKey(id);
	}

	public Appuser findUserByUsername(String username) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Appuser> query = cb.createQuery(Appuser.class);
        Root<Appuser> root = query.from(Appuser.class);
        query.where(
                cb.equal(root.get(Appuser_.USERNAME), username)
        );
        return getResultOrNullFromQuery(query);
	}

    public Appuser findUserByEmail(String email) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Appuser> query = cb.createQuery(Appuser.class);
        Root<Appuser> root = query.from(Appuser.class);
        query.where(
                cb.equal(root.get(Appuser_.EMAIL), email)
        );
        return getResultOrNullFromQuery(query);
    }

    public boolean isUsernameUnique(Integer id, String username) {
		Appuser appuser = findUserByUsername(username);
        return ( appuser == null || ((id != null) && (appuser.getId() == id)));
	}

    public boolean isEmailUnique(Integer id, String email) {
        Appuser appuser = findUserByEmail(email);
        return ( appuser == null || ((id != null) && (appuser.getId() == id)));
    }

	public void saveIn(Appuser appuser) {
		persist(appuser);
	}

}
