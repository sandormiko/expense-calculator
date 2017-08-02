package expense.calculator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import expense.calculator.domain.Expense;
import expense.calculator.module.ServerModule;
import expense.calculator.resource.ExpensesResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ServerApplication extends Application<ServerConfiguration> {

	public static void main(final String[] args) throws Exception {
		new ServerApplication().run(args);
	}

	private final HibernateBundle<ServerConfiguration> hibernate = new HibernateBundle<ServerConfiguration>(
			Expense.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(ServerConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
		bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
		bootstrap.addBundle(new AssetsBundle("/assets/js", "/js", null, "js"));
		bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/fonts", null, "fonts"));
	}

	@Override
	public void run(ServerConfiguration configuration, Environment environment) throws Exception {
		Injector injector = Guice.createInjector(new ServerModule(hibernate));
		environment.jersey().setUrlPattern("/app/*");
		environment.jersey().register(injector.getInstance(ExpensesResource.class));

	}

}
