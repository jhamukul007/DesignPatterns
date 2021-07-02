package com.design.patterns.genericsort;


import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Sort<T> {
        @Getter
        private SortBy sortBy;
        @Getter
        private SortOrder sortOrder;

        /* Only For Date
         * It is useful for date when you try to sort date by current time (like nearest date)
         * Input: List<YestardayDate(expired date), TodayDate, dayAfterTomorrowDate, tomorrow>
         * Output: List<TodayDate(if it is not expired),tomorrow, dayAfterTomorrowDate,YestardayDate>
         */
        @Getter
        private boolean sortFromNow;

        private Sort(SortBuilder<T> builder) {
            this.sortBy = builder.sortBy;
            this.sortOrder = builder.sortOrder;
            this.sortFromNow = builder.sortFromNow;
        }

        public static class SortBuilder<T>{
            private SortBy sortBy;
            private SortOrder sortOrder;
            private boolean sortFromNow;

            public SortBuilder<T> setSortBy(SortBy sortBy) {
                this.sortBy=sortBy;
                return this;
            }
            public SortBuilder<T> setSortOrder(SortOrder sortOrder) {
                this.sortOrder=sortOrder;
                return this;
            }
            public SortBuilder<T> setSortFromNow(boolean value){
                this.sortFromNow=value;
                return this;
            }

            public Sort<T> build(){
                return new Sort<T>(this);
            }
        }

        public enum SortOrder{
            ASC,DESC;
        }
        /**
         * enum_Name("Class field name");
         * Suggestion: Use  sort(entity, class field name)
         */
        public enum SortBy{
            NAME("name"),
            CUSTOM("Custom");

            String fieldName;

            SortBy(String fieldName){
                this.fieldName=fieldName;
            }

            public String getFieldName() {
                return this.fieldName;
            }

            private SortBy setFieldName(String fieldName) {
                this.fieldName=fieldName;
                return this;
            }
        }

        public void sort(List<T> entity) {
            genericSort(entity, this.getSortBy());
        }
        /**
         * Make sure sortBy exist on T or parent of T.
         * Alternative of SortBy enum, no need to set SortBy
         * @param entity list of entity
         * @param sortBy is the name of class attribute
         */
        public void sort(List<T> entity, String sortBy) {
            SortBy sortByEnum=SortBy.CUSTOM.setFieldName(sortBy);
            genericSort(entity, sortByEnum);
        }

        private void genericSort(List<T> entity, final SortBy sortBy) {
            try {
                Field field=getField(entity, sortBy);
                if(field==null)
                    throw new RuntimeException("Field must not be null");
                genericSort(entity, sortBy,field);
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        private void genericSort(List<T> entity, final SortBy sortBy, final Field field ) throws NoSuchFieldException, SecurityException {
            Collections.sort(entity,new Comparator<T>() {

                @SuppressWarnings("rawtypes")
                @Override
                public int compare(T obj1, T obj2) {
                    try {
                        Object value1=field.get(obj1);
                        Object value2=field.get(obj2);
                        Comparable value11=(Comparable) field.get(obj1);
                        Comparable value12=(Comparable) field.get(obj2);
                        return SortOrder.ASC.equals(getSortOrder())?sortByAsc(value1, value2, value11, value12):sortByDESC(value1, value2, value11, value12);

                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return 0;
                }

            });
        }

        private final Field getField(List<T> entity, SortBy sortBy) throws IllegalArgumentException , NoSuchFieldException, SecurityException {
            if(entity==null || entity.isEmpty())
                throw new IllegalArgumentException ("entity musn't be null");
            T attribute=(T) entity.get(0);
            if(attribute==null)
                return null;
            Field field=null;
            try {
                field=attribute.getClass().getDeclaredField(sortBy.getFieldName());
            }
            catch (Exception e) {
                field=attribute.getClass().getSuperclass().getDeclaredField(sortBy.getFieldName());
            }

            field.setAccessible(true);
            return field;
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        private int sortByAsc(Object o1,Object o2, Comparable o11, Comparable o22) {
            if(o1==null && o2==null)
                return 0;
            else if(o1!=null && o2==null)
                return -1;
            else if(o1==null && o2!=null)
                return 1;

            else {
                //Nearest to now not past date
                if(o1 instanceof Date && o2 instanceof Date && this.isSortFromNow()) {
                    long d1=((Date)o1).getTime()-new Date().getTime();
                    long d2=((Date)o2).getTime()-new Date().getTime();
                    if(d1<0 && d2<0)
                        return d1>d2?-1:1;
                    if(d1<0 && d2>0)
                        return 1;
                    else if(d1>0 && d2<0)
                        return -1;
                    else if(d1>0 && d2>0)
                        return d1>d2?1:-1;
                    else
                        return 0;
                }
                return o11.compareTo(o22);
            }
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private int sortByDESC(Object o1,Object o2, Comparable o11, Comparable o22) {
            if(o1==null && o2==null)
                return 0;
            else if(o1!=null && o2==null) {
                return 1;
            }
            else if(o1==null && o2!=null) {
                return -1;
            }
            else {

                //Nearest to now not past date
                if(o1 instanceof Date && o2 instanceof Date && this.isSortFromNow()) {
                    long d1=((Date)o1).getTime()-new Date().getTime();
                    long d2=((Date)o2).getTime()-new Date().getTime();
                    if(d1<0 && d2<0)
                        return d1>d2?-1:1;
                    if(d1<0 && d2>0)
                        return 1;
                    else if(d1>0 && d2<0)
                        return -1;
                    else if(d1>0 && d2>0)
                        return d1>d2?-1:1;
                    else
                        return 0;
                }
                return o22.compareTo(o11);
            }
        }
    }
