package QuadTree;

import java.util.ArrayList;
import java.util.List;

import nbodysystem.Body;

public class QuadTree {
    private static final int MAX_POINTS = 1;
    private Region area;
    private List<Body> bodies = new ArrayList<>();
    private List<QuadTree> quadTrees = new ArrayList<>();

    public QuadTree(Region area) {
        this.area = area;
    }
    
    public boolean addBody(Body body) {
        if (this.area.containsBody(body)) {
            if (this.bodies.size() < MAX_POINTS) {
                this.bodies.add(body);
                return true;
            } else {
                if (this.quadTrees.size() == 0) {
                    createQuadrants();
                }
                return addBodyToOneQuadrant(body);
            }
        }
        return false;
    }
    
    private boolean addBodyToOneQuadrant(Body body) {
        boolean isBodyAdded;
        for (int i = 0; i < 4; i++) {
        	isBodyAdded = this.quadTrees.get(i).addBody(body);
            if (isBodyAdded)
                return true;
        }
        return false;
    }
    
    private void createQuadrants() {
        Region region;
        for (int i = 0; i < 4; i++) {
            region = this.area.getQuadrant(i);
            quadTrees.add(new QuadTree(region));
        }
    }
    
    public List<Body> search(Region searchRegion, List<Body> matches) {
        if (matches == null) {
            matches = new ArrayList<Body>();
        }
        if (!this.area.doesOverlap(searchRegion)) {
            return matches;
        } else {
            for (Body body : bodies) {
                if (searchRegion.containsBody(body)) {
                    matches.add(body);
                }
            }
            if (this.quadTrees.size() > 0) {
                for (int i = 0; i < 4; i++) {
                    quadTrees.get(i).search(searchRegion, matches);
                }
            }
        }
        return matches;
    }
    
    
}
