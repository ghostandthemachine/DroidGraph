package com.android.droidgraph.settings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

/**
 * A simple class to store and retrieve internal Scenario settings in the form of 
 * String key/value pairs.  It is meant to be used in a similar way to System
 * Properties, but without the security  restrictions.  This class is designed
 * primarily to aid in testing and benchmarking Scenario itself.
 * 
 * If you are running in an environment that allows System Property access,
 * this class will attempt to look for a key's valuein the System Properties if
 * none is found in Settings.  This allows Settings to be set on the command
 * line as well as via the Settings API.
 * 
 */
public class Settings {

    private static HashMap<String, String> settings =
            new HashMap<String, String>(5);
    private static PropertyChangeSupport pcs =
            new PropertyChangeSupport(Settings.class);

    /**
     * Add a new key-value setting.
     * 
     * Passing a value of null indicates that the value for this key should be
     * looked for in the System Properties.
     * 
     * If PropertyChangeListeners have been registered for the given key, they
     * will be notified of a change in value.
     *
     * If key is "" or null, this methods throws an IllegalArgumentException.
     */
    public static void set(String key, String value) {
        checkKeyArg(key);
        String oldVal = get(key);
        settings.put(key, value);
        String newVal = value;
        if (newVal == null) {
            newVal = get(key);
        }
        pcs.firePropertyChange(key, oldVal, newVal);
    }

    /**
     * Retrieve the value for the given key.
     * 
     * If the key is not present in Settings or its value is null, this methods
     * then checks to see if a value for this key is present in the System
     * Properties (provided you have sufficient privileges).
     * 
     * If no value can be found for the given key, this method returns null.
     * 
     * If key is "" or null, this methods throws an IllegalArgumentException.
     */
    public static String get(String key) {
        checkKeyArg(key);
        String retVal = settings.get(key);
        if (retVal == null) {
            try {
                retVal = System.getProperty(key);
            } catch (SecurityException ignore) {
            }
        }
        return retVal;
    }

    /**
     * Convenience method for boolean settings.
     * 
     * If the setting exists and its value is "true", true is returned.
     * Otherwise, false is returned.
     * 
     * If key is "" or null, this methods throws an IllegalArgumentException.
     */
    public static boolean getBoolean(String key) {
        // get() will call checkKeyArg(), so don't check it here
        String value = get(key);
        return "true".equals(value);
    }

    /**
     * Add a PropertyChangeListener for the specified setting
     *
     * Note that the PropertyChangeEvent will contain 
     * old and new values as they would be returned from get(), meaning they
     * may come from the System Properties.
     * 
     * If key is "" or null, this methods throws an IllegalArgumentException.
     * If listener is null no exception is thrown and no action is taken.
     */
    public static void addPropertyChangeListener(String key,
            PropertyChangeListener pcl) {
        checkKeyArg(key);
        pcs.addPropertyChangeListener(key, pcl);
    }

    /**
     * Remove the specified PropertyChangeListener.
     * 
     * If listener is null, or was never added, no exception is thrown and no
     * action is taken.
     */
    public static void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    /*
     * Check that key is a valid Settings key.  If not, throw an
     * IllegalArgumentException. 
     *
     */
    private static void checkKeyArg(String key) {
        if (null == key || "".equals(key)) {
            throw new IllegalArgumentException("null key not allowed");
        }
    }

    private Settings() {
    }
}
