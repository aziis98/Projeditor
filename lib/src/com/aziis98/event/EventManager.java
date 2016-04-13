package com.aziis98.event;

// Copyright 2016 Antonio De Lucreziis

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.*;

public class EventManager {

    private static final HashMap<Class, LinkedList<MethodAndObject>> eventBus = new HashMap<>();

    /**
     * All the methods with a single argument that extends IEvent gets registered to the event manager<br>
     * <br>
     * <strong>Annotation Setting</strong>:
     * <ul>
     *     <li>
     * The <code>@Priority</code> annotation changes the priority of a registered method.
     * The invoker calls the registered methods from the one with higher priority to the one with the lower
     *     </li>
     * </ul>
     *
     * @param object The object instance to analyze
     */
    public static void register(Object object) {
        Class theClass = object.getClass();

        for (Method aMethod : theClass.getDeclaredMethods())
        {
            Class<?>[] parameterTypes = aMethod.getParameterTypes();
            if ( parameterTypes.length == 1 && IEvent.class.isAssignableFrom( parameterTypes[0] ) )
            {
                // System.out.println( "Found method: " + aMethod.toGenericString() );

                registerMethod( object, parameterTypes[0].asSubclass( IEvent.class ), aMethod );
            }
        }

        for (Entry<Class, LinkedList<MethodAndObject>> classTreeSetEntry : eventBus.entrySet())
        {
            // System.out.println( "Class: " + classTreeSetEntry.getKey().toGenericString() );

            for (MethodAndObject methodAndObject : classTreeSetEntry.getValue())
            {
                // System.out.println( "    " + methodAndObject.method.toGenericString() );
            }
        }
    }

    private static <E extends IEvent> void registerMethod(Object object, Class<E> eventType, Method method) {
        LinkedList<MethodAndObject> methodAndObjects = getBus( eventType );
        methodAndObjects.add( new MethodAndObject( method, object ) );
        methodAndObjects.sort( METHOD_COMPARATOR );
    }

    public static <E extends IEvent> void invoke(E eventPacket) {
        getBus( eventPacket.getClass() ).forEach( methodAndObject -> {
            try
            {
                methodAndObject.method.invoke( methodAndObject.object, eventPacket );
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        } );
    }

    public static <E extends IEvent> LinkedList<MethodAndObject> getBus(Class<E> classType) {
        LinkedList<MethodAndObject> methodTreeSet = eventBus.get( classType );

        // System.out.println(classType.toString() + " -> " + methodTreeSet);

        if ( methodTreeSet == null )
        {
            eventBus.put( classType, methodTreeSet = new LinkedList<>() );
        }

        return methodTreeSet;
    }

    public static final Comparator<MethodAndObject> METHOD_COMPARATOR = Comparator.comparingInt( (MethodAndObject m) -> {
        Priority priorityAnnotation = m.method.getAnnotation( Priority.class );
        if ( priorityAnnotation == null )
        {
            return 0;
        }
        return priorityAnnotation.value();
    } ).reversed();


    private static class MethodAndObject {
        public Method method;
        public Object object;

        public MethodAndObject(Method method, Object object) {
            this.method = method;
            this.object = object;
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            MethodAndObject that = (MethodAndObject) o;

            return method.equals( that.method ) && object.equals( that.object );
        }

        @Override
        public int hashCode() {
            int result = method.hashCode();
            result = 31 * result + object.hashCode();
            return result;
        }
    }

}
