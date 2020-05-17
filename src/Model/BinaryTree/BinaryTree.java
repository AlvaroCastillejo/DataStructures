package Model.BinaryTree;

import Database.StoreObject;
import Utils.JsonUtils;

import java.util.Arrays;

public class BinaryTree {

    StoreObject[] storeObjects;

    public BinaryTree(int size) {
        storeObjects = new StoreObject[(int) (Math.pow(size, 2) - 1)];
        Arrays.fill(storeObjects, new StoreObject());
    }

    private void insertElement(StoreObject object, int i) {

        if (storeObjects[0].getPrice() == -1) {
            storeObjects[0] = object;
            return;
        }

        if (object.getPrice() < storeObjects[i].getPrice()) {
            if (storeObjects[i].getPrice() == -1) {
                storeObjects[i] = object;
            }else {
                i = (i * 2) + 1; //Left Son
                insertElement(object, i);
            }
            return;
        }

        if (object.getPrice() >= storeObjects[i].getPrice()) {
            if (storeObjects[i].getPrice() == -1) {
                storeObjects[i] = object;
            }else {
                i = (i * 2) + 2; //RightSon
                insertElement(object, i);
            }
        }

    }



    public static void main(String[] args) {
        StoreObject[] storeObjects = JsonUtils.getStoreObjects("dataset_ObjectS");
        BinaryTree binaryTree = new BinaryTree(storeObjects.length);

        for (StoreObject object: storeObjects) {
            binaryTree.insertElement(object, 0);
        }

        System.out.println("Alvaro");

    }
}
