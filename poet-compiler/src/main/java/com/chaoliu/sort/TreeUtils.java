package com.chaoliu.sort;

import com.chaoliu.utils.Consts;

import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * 树形结构
 *
 * @author chentong
 * @date 2019/03/14
 */
public class TreeUtils {

    private ElementTree rootTree = new ElementTree();
    private List<ElementTree> treeList = new ArrayList<>();

    private int treeSize = 0;

    public TreeUtils() {
    }

    public Map<TypeElement, List<Element>> supportSuperAnnotation(Map<TypeElement, List<Element>> map) {
        treeList.clear();
        newTreeList( map );
        newRootTree();
        makeTree( treeList, rootTree );
        return formatMap();
    }

    /**
     * 数据类型变换
     *
     * @param map
     * @return
     */
    private List<ElementTree> newTreeList(Map<TypeElement, List<Element>> map) {

        if (MapUtils.isNotEmpty( map )) {
            for (Map.Entry<TypeElement, List<Element>> entry : map.entrySet()) {
                TypeElement typeElement = entry.getKey();
                List<Element> elementList = entry.getValue();
                ElementTree elementTree = new ElementTree();
                elementTree.setTypeElement( typeElement );
                elementTree.setElementList( elementList );
                treeList.add( elementTree );
            }
        }

        treeSize = treeList.size();

        return treeList;
    }

    /**
     * 新建根节点
     *
     * @return
     */
    private ElementTree newRootTree() {

        if (!isAllSorted()) {
            rootTree.setParentNode( null );
            rootTree.setElementList( null );
            rootTree.setTypeElement( null );

            for (ElementTree elementTree : treeList) {
                TypeElement typeElement = elementTree.getTypeElement();
                if (isActivity( typeElement ) || isFragment( typeElement )) {
                    elementTree.setParentNode( rootTree );
                    elementTree.setSorted( true );
                    treeSize--;
                    rootTree.getChildNodeList().add( elementTree );
                }
            }
        }

        return rootTree;
    }


    private void makeTree(List<ElementTree> treeList, ElementTree parentNode) {

        if (isAllSorted()) return;

        for (ElementTree childNode : parentNode.getChildNodeList()) {

            for (ElementTree element : treeList) {
                if (element.isSorted()) continue;
                //子节点 = list表中元素
                String clazzName = element.getTypeElement().getSuperclass().toString();
                String childNodeName = childNode.getTypeName();
                if (childNodeName.equals( clazzName )) {
                    --treeSize;
                    element.setSorted( true );
                    element.setParentNode( childNode );
                    childNode.getChildNodeList().add( element );
                    if (isAllSorted()) return;
                    makeTree( treeList, childNode );
                }
            }
        }
    }

    private boolean isAllSorted() {
        if (treeSize == 0) return true;
        return false;
    }

    //遍历整合数据
    private Map<TypeElement, List<Element>> formatMap() {

        Map<TypeElement, List<Element>> hashMap = new HashMap<>();

        for (ElementTree elementTree : treeList) {

            List<List<Element>> elements = new ArrayList<>();
            //增加注解去重功能 fieldName 去重复
            Set<String> sets = new HashSet<>();

            TypeElement typeElement = elementTree.getTypeElement();
            List<Element> elementList = new ArrayList<>();

            do {
                sets.addAll( elementTree.getFieldNameSets() );
                elements.add( elementTree.getElementList() );
            } while ((elementTree = elementTree.parentNode) != null);

            for (String fieldName : sets) {
                boolean isFind = false;
                for (List<Element> list : elements) {
                    if (list == null) break;
                    for (Element element : list) {
                        String name = element.getSimpleName().toString();
                        if (name.equals( fieldName )) {
                            isFind = true;
                            elementList.add( element );
                            break;
                        }
                    }

                    if (isFind) break;
                }
            }

            hashMap.put( typeElement, elementList );
        }

        return hashMap;
    }

    //判断当前是不是activity类
    public boolean isActivity(TypeElement typeElement) {
        String clazzName = typeElement.getSuperclass().toString();
        if (Consts.ACTIVITY.equals( clazzName ) || Consts.ACTIVITY_V7.equals( clazzName )) return true;
        return false;
    }

    //判断当前类是fragment
    public boolean isFragment(TypeElement typeElement) {
        String clazzName = typeElement.getSuperclass().toString();
        if (Consts.FRAGMENT.equals( clazzName) || Consts.FRAGMENT_V4.equals( clazzName )){
            return true;
        }
        return false;
    }

}
