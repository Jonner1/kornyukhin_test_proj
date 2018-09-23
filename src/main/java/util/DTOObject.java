package util;

import cashes.CacheTop;

import java.io.Serializable;

public class DTOObject implements Serializable {

    private long serialVersionUID = 1;

    private Long lifetime = 10000L;
    private long id;
    private SomeObject someObject;

    public boolean isDead(){
        return this.lifetime < System.currentTimeMillis();
    }

    public DTOObject(String something){
        this.id = CacheTop.inc();
        this.lifetime += System.currentTimeMillis();
        this.someObject = new SomeObject(something);
    }

    public DTOObject(String something, Long lifetime){
        this.id = CacheTop.inc();
        this.lifetime = lifetime + System.currentTimeMillis();
        this.someObject = new SomeObject(something);
    }

    public long getLifetime() {
        return lifetime;
    }

    public SomeObject getSomeObject() {
        return someObject;
    }

    public void setSomeObject(SomeObject someObject) {
        this.someObject = someObject;
    }

    public long getId() {
        return id;
    }

    public boolean equals(DTOObject dtoObject){
        return dtoObject != null && dtoObject.getClass() == DTOObject.class ? v(dtoObject.getSomeObject()) : false;

    }

    boolean v(SomeObject someObject){
        return someObject != null ? someObject.equals(this.someObject) : false;
    }

    @Override
    public String toString(){
        return new StringBuilder().append("[").append(this.someObject.toString()).append(": ").append(id).append("]").toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return 17 * prime + (this.someObject != null ? this.someObject.hashCode() : 0);
    }

}
