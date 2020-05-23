package Utils;

import Database.StoreObject;

//Not used in this version.
public class List {
    public static StoreObject[] addStoreObjectAtEnd(StoreObject object, StoreObject[] to, int index){
        StoreObject[] newObjectArray = new StoreObject[to.length+1];

        for (int i = 0; i < to.length; i++) {
            StoreObject aux = to[i];
            newObjectArray[i] = aux;
        }

        newObjectArray[newObjectArray.length-1] = object;
        return newObjectArray;
    }

    public static StoreObject[] addStoreObjectAtEnd(StoreObject object, StoreObject[] to){
        StoreObject[] newObjectArray = new StoreObject[to.length+1];

        for (int i = 0; i < to.length; i++) {
            StoreObject aux = to[i];
            newObjectArray[i] = aux;
        }

        newObjectArray[newObjectArray.length-1] = object;
        return newObjectArray;
    }

    public static StoreObject[] removeStoreObject(int index, StoreObject[] from){
        StoreObject[] newObjectArray = new StoreObject[from.length-1];

        int i;
        for(i = index; i < from.length; i++){
            try{
                from[i] = from[i+1];
            } catch (IndexOutOfBoundsException ex){
                break;
            }
        }

        for(i = 0; i < from.length-1; i++){
            newObjectArray[i] = from[i];
        }

        return newObjectArray;
    }
}
