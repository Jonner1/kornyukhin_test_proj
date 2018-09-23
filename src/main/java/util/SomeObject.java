package util;

import cashes.CacheTop;

public class SomeObject {

   // private long id;

    private String somevalue;

    protected SomeObject(String somevalue){
        //this.id = CacheTop.inc();
        this.somevalue = somevalue;
    }

    public String getSomevalue() {
        return somevalue;
    }

    public void setSomevalue(String somevalue) {
        this.somevalue = somevalue;
    }

//    public long getId() {
//        return id;
//    }

    @Override
    public boolean equals(Object someObject){
        return someObject != null && someObject == this && someObject.getClass() == this.getClass() ? eq((SomeObject) someObject) : false;
    }

    private boolean eq(SomeObject someObject){
        return someObject.getSomevalue() != null ? someObject.getSomevalue().equals(this.getSomevalue()) : false;
    }

    @Override
    public int hashCode() {
        final int prime = 27;
        return 37 * prime + (this.getSomevalue() != null ? this.getSomevalue().hashCode() : 0);
    }

    @Override
    public String toString(){
        return new StringBuilder().append(somevalue).toString();
    }
}
