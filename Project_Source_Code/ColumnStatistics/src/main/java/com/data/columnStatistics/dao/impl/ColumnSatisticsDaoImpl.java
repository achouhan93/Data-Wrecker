package com.data.columnStatistics.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.data.columnStatistics.dao.ColumnStatisticsDao;
import com.data.columnStatistics.entity.QuestionsEntity;

@Repository
public class ColumnSatisticsDaoImpl implements ColumnStatisticsDao{
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<QuestionsEntity> getColumnValues(String columnName) {
		Session session = sessionFactory.getCurrentSession();
//		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//		CriteriaQuery<QuestionsEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionsEntity.class);
//		Root<QuestionsEntity> questionsRoot = criteriaQuery.from(QuestionsEntity.class);
//		criteriaQuery.select(questionsRoot);
//		List<QuestionsEntity> listOfQColumnValues = session.createQuery(criteriaQuery).getResultList();

		@SuppressWarnings("unchecked")
		List<QuestionsEntity> listOfQColumnValues = session
				.createQuery("select q." + columnName + " \r\n" + "from QuestionsEntity q ").list();
		return listOfQColumnValues;
	}

}
