package com.staf.utilities.parser;

public class Parser {
    /**
     * Refactored method to parse an object, attValue, from it's unknown type to the type expected.
     *
     * @param attValue The attribute value to parse
     * @param type     The type expected/to convert to
     * @return The attribute value in the expected type, null otherwise
     */
    public static Object parseType(Object attValue, Class<?> type) {
        Object newAttValue = null;

        if (type == String.class) {
            newAttValue = attValue.toString();
        } else if (type == Byte.class || type == byte.class) {
            newAttValue = Byte.valueOf(attValue.toString());
        } else if (type == Character.class || type == char.class) {
            newAttValue = attValue.toString().charAt(0);
        } else if (type == Integer.class || type == int.class) {
            newAttValue = Integer.valueOf(attValue.toString());
        } else if (type == Short.class || type == short.class) {
            newAttValue = Short.valueOf(attValue.toString());
        } else if (type == Long.class || type == long.class) {
            newAttValue = Long.valueOf(attValue.toString());
        } else if (type == Float.class || type == float.class) {
            newAttValue = Float.valueOf(attValue.toString());
        } else if (type == Double.class || type == double.class) {
            newAttValue = Double.valueOf(attValue.toString());
        } else if (type == Boolean.class || type == boolean.class) {
            newAttValue = Boolean.valueOf(attValue.toString());
        } //if-else if*

        return newAttValue;
    } //parseType
}
