package com.demo.imdb.service.relationships;

import java.util.*;

public class PersonRelationshipSearcher implements RelationshipSearcher {

    private final String goal;
    private final RelationshipsLoader loader;

    private Queue<String> nodeQueue = new LinkedList<>();
    private int relationshipDepth = -1;
    private Set<String> processed = new HashSet<>();
    private boolean found = false;

    public PersonRelationshipSearcher(String goal, RelationshipsLoader loader) {
        this.goal = goal;
        this.loader = loader;
    }

    @Override
    public int findRelationshipDegree(String source) {
        nodeQueue.add(source);
        findRelationshipDegreeInternal();
        return found ? relationshipDepth : -1;
    }

    //v1
    /*private void findRelationshipDegreeInternal() {
        String parent = nodeQueue.poll();

        if (processed.contains(parent))
            return;

        processed.add(parent);
        List<String> children = loader.loadChildren(parent);

        for (String child : children) {
            if (child.equals(goal))
                return;
            else nodeQueue.add(child);
        }

        findRelationshipDegreeInternal();
    }
*/
    private void findRelationshipDegreeInternal() {

        while (!nodeQueue.isEmpty()) {
            String current = nodeQueue.poll();

            for (String child : loader.loadChildren(current)) {
                if (child.equals(goal)) {
                    found = true;
                    return;
                }
                if (!processed.contains(child) && !nodeQueue.contains(child))
                    nodeQueue.add(child);
            }
            processed.add(current);
            ++relationshipDepth;
        }
    }
}
