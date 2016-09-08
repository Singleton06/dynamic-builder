package com.singleton.dynamic.builder.internal.common;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class for performing common functions with {@link Collection}s.
 * 
 * @author Dustin Singleton
 */
public class CollectionUtil
{
    /**
     * <p>
     * Copies the specified collection using an unspecified implementation of collections.
     * </p>
     * <p>
     * <strong>Note: </strong> this will very likely change the internal implementation of the collection.
     * </p>
     * 
     * @param collectionToCopy
     *            The collection that will be copied.
     * @return the resulting collection, which will have all values from the {@code collectionToCopy}, but could be of a
     *         different implementation.
     */
    public static <T> Collection<T> copyCollection(Collection<T> collectionToCopy)
    {
        if (collectionToCopy == null)
        {
            return new ArrayList<T>(0);
        }

        Collection<T> collectionToReturn = new ArrayList<T>(collectionToCopy.size());
        for (T item : collectionToCopy)
        {
            collectionToReturn.add(item);
        }
        return collectionToReturn;
    }
}
