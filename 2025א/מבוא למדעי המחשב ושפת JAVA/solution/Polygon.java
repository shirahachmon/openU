
/**
 *  Judy Isaacs and Joyce Vogel
 *  This class represents a "Convex Polygon" in the plane
 */
public class Polygon
{
        private Point [ ] _vertices;
        private int _noOfVertices;
        private final int MAX = 10;         
         
        /**
         * Creates an empty polygon.
         */
        
        public Polygon()
        {
             _noOfVertices = 0;
             _vertices =  new Point[MAX];
             for (int i = 0;  i < MAX; i++)
                _vertices[i] = null;
        }
         
        /**
        * Adds a new Point to the Polygon with the given x and y values
        * @param x  the specified x coordinate
        * @param y  the specified y coordinate
        * @return true if the point was added successfully; false otherwise.
        */
        public boolean addVertex(double x, double y){
             
             if( _noOfVertices ==   MAX)
                return false;
             _vertices[_noOfVertices++] = new Point(x, y);
             return true;
        }
            
        /**
        * Finds and returns a copy of the highest Point on the Polygon if such a point exists. If it does not exist,
        * returns null.  In the event that more than one such point exists, it returns the first point it finds.
        * @return a Point which represents the highest vertex on the polygon if such a point exists;
        * false otherwise. 
        */
        public Point highestVertex()
        {
            if (_noOfVertices == 0)
                    return null;
            Point maxPoint = _vertices[0];
            double maxY = _vertices[0].getY();
                
            for(int i = 1; i < _noOfVertices; i++)
                if(maxY < _vertices[i].getY()){
                   maxPoint = _vertices[i];
                   maxY = _vertices[i].getY();    
            }        
            return new Point(maxPoint);
        }
            
        /**
        * Returns a string representation of this Polygon.  
        * @return a string representation of this Polygon
        */
        public String toString()
        {
            if (_noOfVertices == 0)
                return "The polygon has 0 vertices.";
            String str = "The polygon has " + _noOfVertices+  " vertices:\n(";
            for (int i = 0; i < _noOfVertices; i++)
            {
                 str += "(" +_vertices[i].getX()+"," + _vertices[i].getY() +")";
                 if (i<_noOfVertices-1)
                    str += ",";
            }
            str += ")";
            return str;
           }
           
        /**
        * Calculates the area of a triangle, using Heron's formula.
        * @param point1 represents the first point of the triangle
        * @param point2 represents the second point of the triangle
        * @param point3 represents the third point of the triangle
        * @return the area of the triangle
        */  
            
        private double heronFormula(Point point1, Point point2, Point point3){
                double a = point1.distance(point2);
                double b = point2.distance(point3);
                double c = point3.distance(point1);
                double s = (a + b + c)/2;
                return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        }
           
        /**
        * Calculates and returns the area of the Polygon. In the event that the Polygon has less than three 
        * verticies, it returns 0.
        * @return the area of the Polygon if the Polygon has 3 or more verticies; otherwise 0. 
        */  
        public double calcArea(){
    
                double area=0;
                int i;
                for(i=0; i<_noOfVertices-2; i++)
                   area+= heronFormula(_vertices[0], _vertices[i+1] ,_vertices[i+2]);
                return area;
        }
        
    
       /**
       * Calculates and returns the perimeter of the Polygon. In the event that there are 2 verticies, 
       * it calculates the distance of the line represented by the two verticies.  In the event that there
       * are less than 2 verticies it returns 0.
       * @return the perimeter of the Polygon if the Polygon has 3 or more verticies; otherwise if it has 
       * 2 verticies returns the distance between the verticies; otherwise 0.  
       */  
    
        public double calcPerimeter(){
            
            if(_noOfVertices < 2)
                return 0;
                
            double perimeter = 0;
            for (int i = 0; i < _noOfVertices - 1; ++i)
                    perimeter += _vertices[i].distance(_vertices[i + 1]);
            if(_noOfVertices > 2)
                perimeter += _vertices[_noOfVertices - 1].distance( _vertices[0]);

            return perimeter;
       }  
       
        /**
        * Determines whether or not the area of Polygon is bigger than the area of the instance of 
        * Polygon it is to be compared with.
        * @param other an object to be compared with this Polygon
       * @return true if this Polygon has an area larger than the object to be compared with; 
       * false otherwise
       */  
       public boolean isBigger(Polygon other){
            return  calcArea() > other.calcArea();
       }
       
       /**
       * Finds and returns the index of a vertex.  
       * @param p the Point whose index will be returned
       * @return the index of p if the Point exists in the Polygon;  otherwise-1
       */    
       public int findVertex(Point p){
            for(int i= 0; i < _noOfVertices; i++)
                if(_vertices[i].equals(p))
                    return i;
            return -1;
       }   
       
       /**
       * Finds and returns a copy of the successor of a Point in the Polygon.  
       * @param p the Point whose successor will be returned
       * @return the successor of p if  Point exists in the Polygon;  otherwise null
       */      
       public Point getNextVertex(Point p){
            int i = findVertex(p);
            if (i== -1)
                return null;
            return new Point( _vertices[(i+1)%_noOfVertices]);
       }
       
       /**
       * Calculates and returns a Polygon which represents the bounding box of this Polygon. 
       * The bounding box is the smallest Rectangle whose sides are parallel to the x and y axes of the 
       * coordinate space, and can completely contain the Polygon. 
       * @return a Polygon (in the shape of a rectangle) that defines the bounds of this Polygon
       */ 
    
       public Polygon getBoundingBox() {
         
         if (_noOfVertices < 3)
            return null;
        
          double highest = this.highestVertex().getY();    
          double rightmost = this.rightMostVertex().getX();       
          double lowest = this.lowestVertex().getY();
          double leftmost = this.leftMostVertex().getX();           
       
          Polygon boundingBox = new Polygon();
          boundingBox.addVertex(leftmost,highest);
          boundingBox.addVertex(rightmost,highest);
          boundingBox.addVertex(rightmost, lowest);
          boundingBox.addVertex(leftmost, lowest);      
          return boundingBox;
      }
      
      /**
      * Finds and returns a copy of the lowest Point on the Polygon.  In the event that more than one such point exists, it returns the first point it finds.
      * @return a Point which represents the lowest vertex on the polygon.
      */
      private Point lowestVertex() {
        Point lowestPoint = _vertices[0];
        for (int i=1; i<(_noOfVertices); i++) {
            if (_vertices[i] .isUnder(lowestPoint))
                lowestPoint = _vertices[i];
        }
        return new Point(lowestPoint);
      }    
      
      /**
      * Finds and returns a copy of the rightmost Point on the Polygon.  In the event that more than one such point exists, it returns the first point it finds.
      * @return a Point which represents the rightmost vertex on the polygon.
      */ 
      private Point rightMostVertex() {
    
        Point rightestPoint = _vertices[0];
        for (int i=1; i<(_noOfVertices); i++) {
            if (_vertices[i] .isRight(rightestPoint))
                rightestPoint = _vertices[i];
        }
        return new Point(rightestPoint);
      }    
      
       /**
      * Finds and returns a copy of the leftmost Point on the Polygon.  In the event that more than one such point exists, it returns the first point it finds.
      * @return a Point which represents the leftmost vertex on the polygon.
      */ 
 
      private Point leftMostVertex() {
  
        Point leftestPoint = _vertices[0];
        for (int i=1; i<(_noOfVertices); i++) {
            if (_vertices[i] .isLeft(leftestPoint))
                leftestPoint = _vertices[i];
        }
        return new Point(leftestPoint);
      }        
    
}
        

