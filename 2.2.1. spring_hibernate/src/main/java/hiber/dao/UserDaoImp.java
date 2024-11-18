package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUser(String model, int series) {
      TypedQuery<Car> queryCar = sessionFactory.getCurrentSession()
              .createQuery("from Car where model = :model and series = :series");
      queryCar.setParameter("model", model);
      queryCar.setParameter("series", series);

      Car car = queryCar.getSingleResult();

      TypedQuery<User> queryUser = sessionFactory.getCurrentSession()
                      .createQuery("from User where car_id = :id");
      queryUser.setParameter("id", car.getId());
      return queryUser.getSingleResult();
   }
}
