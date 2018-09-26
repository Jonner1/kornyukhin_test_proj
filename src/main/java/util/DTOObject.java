package util;

import cashes.CacheTop;

import java.io.Serializable;

public class DTOObject implements Serializable {

    private long serialVersionUID = 1;

    private Long lifetime;
    private long id;
   // private SomeObject someObject;

    public boolean isDead(){
        return this.lifetime < System.currentTimeMillis();
    }

    public DTOObject(){
        this.id = CacheTop.inc();
        this.lifetime = System.currentTimeMillis() + 20000L;
        //this.someObject = new SomeObject(something);
    }

    public DTOObject(long id){
        this.id = id;
        this.lifetime = System.currentTimeMillis() + 20000L;
        //this.someObject = new SomeObject(something);
    }

    public DTOObject(String something, Long lifetime){
        this.id = CacheTop.inc();
        this.lifetime = lifetime + System.currentTimeMillis();
       // this.someObject = new SomeObject(something);
    }

    public long getLifetime() {
        return lifetime;
    }

  // public SomeObject getSomeObject() {
      //  return someObject;
   // }

   // public void setSomeObject(SomeObject someObject) {
   //     this.someObject = someObject;
    //}

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object dtoObject){
        return dtoObject != null && dtoObject.getClass() == DTOObject.class ? this.lifetime == ((DTOObject) dtoObject).getLifetime() && this.getId() == ((DTOObject) dtoObject).getId() : false;

    }

    @Override
    public String toString(){
        return new StringBuilder().append("[").append(id).append("]").toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return this.id != 0 ? 17 * prime : 0;
    }

}
