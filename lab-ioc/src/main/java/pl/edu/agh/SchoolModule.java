package pl.edu.agh;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {

    @Provides
    public IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager){
        return persistenceManager;
    }

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

}
