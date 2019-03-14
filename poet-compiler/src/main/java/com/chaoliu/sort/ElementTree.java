package com.chaoliu.sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * 树形结构数据
 * entity
 */
public class ElementTree {

    public ElementTree parentNode = null;
    private List<ElementTree> childNodeList = new ArrayList<>(  );
    private TypeElement typeElement;
    private List<Element> elementList;
    private Set<String> fieldNameSets;
    private String typeName;
    private  boolean isSorted;

    public ElementTree getParentNode() {
        return parentNode;
    }

    public void setParentNode(ElementTree parentNode) {
        this.parentNode = parentNode;
    }

    public List<ElementTree> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<ElementTree> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        if (typeElement == null) return;
        this.typeElement = typeElement;
        setTypeName( typeElement.getQualifiedName().toString() );
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
        Set<String> sets = new HashSet<>();
        if (elementList != null) {
            for (Element element : elementList) {
                sets.add( element.getSimpleName().toString() );
            }
        }
        setFieldNameSets( sets );
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set<String> getFieldNameSets() {
        return fieldNameSets;
    }

    public void setFieldNameSets(Set<String> fieldNameSets) {
        this.fieldNameSets = fieldNameSets;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public void setSorted(boolean sorted) {
        isSorted = sorted;
    }

    @Override
    public String toString() {
        return "ElementTree{" +
                "parentNode=" + parentNode +
                ", childNodeList=" + childNodeList +
                ", typeElement=" + typeElement +
                ", elementList=" + elementList +
                ", fieldNameSets=" + fieldNameSets +
                ", typeName='" + typeName + '\'' +
                ", isSorted=" + isSorted +
                '}';
    }
}