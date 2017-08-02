package expense.calculator.module;

import javax.inject.Singleton;

import expense.calculator.ServerConfiguration;
import expense.calculator.dao.ExpenseDAO;
import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import io.dropwizard.hibernate.HibernateBundle;

public class ServerModule extends AbstractModule {

	private final HibernateBundle<ServerConfiguration> hibernateBundle;

	public ServerModule(HibernateBundle<ServerConfiguration> hibernateBundle) {
		this.hibernateBundle = hibernateBundle;

	}

	@Provides
	@Singleton
	public SessionFactory provideSessionFactory() {
		return hibernateBundle.getSessionFactory();
	}

	@Override
	protected void configure() {
		bind(ExpenseDAO.class);

	}

}
