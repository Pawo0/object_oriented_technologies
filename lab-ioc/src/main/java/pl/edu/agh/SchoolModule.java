package pl.edu.agh;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {

    @Override
    protected  void configure() {
        bind(IPersistenceManager.class).to(SerializablePersistenceManager.class);
    }

// alternative way using @Provides method
//    @Provides
//    public IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager){
//        return persistenceManager;
//    }

    @Provides
    @Named("classesStorage")
    public String provideClassesStorageFileName() {
        return "guice-classes.dat";
    }

    @Provides
    @Named("teachersStorage")
    public String provideTeachersStorageFileName() {
        return "guice-teachers.dat";
    }


    @Provides
    @Singleton
    public Logger provideLogger() {Logger logger = new Logger();
        logger.registerSerializer(new FileMessageSerializer("persistence.log"));
        return logger;
    }
}
