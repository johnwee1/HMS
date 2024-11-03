package repository;

/**
 * Enforces an abstract interface for the template class to implement. Methods here should only be internally called
 * @param <T> Object type
 */
public abstract class AbstractRepository<T> {
    protected abstract void loadDatabase();
    protected abstract void saveDatabase();

    protected abstract void defaultCreateItem(T item);
    protected abstract T defaultReadItem(String id);
    protected abstract void defaultUpdateItem(T item);
    protected abstract void defaultDeleteItem(String id);

}