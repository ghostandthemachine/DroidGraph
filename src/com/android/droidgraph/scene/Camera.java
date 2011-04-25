package com.android.droidgraph.scene;


import com.android.droidgraph.util.Vec3;

/**
 * Not sure what I'm doing here, camera transformations are like multithreading, that is
 * NOT my forte xD But I pulled off multithreading, so here's to a little hope this works 
 * as well.
 * 
 * The camera is a singleton for the LOGIC thread, keeps the position of the OGL camera, 
 * moves it in 3D space as required, and can transform screen to map coordinates.
 * 
 * It's a singleton because the data here is needed and modified in quite a few different places,
 * and honestly everyone has a right to know where the hell the camera is. It is NOT so the render
 * thread can access directly.
 * 
 * It's in the Logic thread because...
 * The render thread is sent a message if the camera changes.
 * The camera should not change much, just when the player cursors go out of it's field of view
 * or they are close enough to zoom in on them. That's why we can afford to message each position 
 * change. 
 * This also allows us to keep things consistent by doing logic calculations (where and when to move)
 * in the logic thread, while the render thread only get's told where to draw.
 * 
 * TODO: Camera is only gonna work correctly for maps where width > height. Fix this by using max/min(w,h)
 * where appropriate.
 * 
 * @author Ying
 *
 */
public class Camera 
{
        /**
         * Static singleton instance
         */
        private static Camera instance = new Camera();
        
        /**
         * Position in ogl coords of the camera
         */
        private Vec3 pos;
        
        /**
         * Screen viewport height
         */
        private int screenH;
        
        /**
         * Screen viewport width
         */
        private int screenW;
        
        /**
         * Minimum camera z
         */
        private int minZ;
        
        /**
         * Maximum camera z
         */
        private int maxZ;
        
        /**
         * Minimum ratio for map/screen
         */
        private float minRatio;
        
        /**
         * Maximum ratio for map/screen
         */
        private float maxRatio;
        
        /**
         * Distance the camera must translate through if asked to move.
         * Used for interpolation
         */
        private double distance;
        
        /**
         * Unitary direction vector for the camera if asked to move.
         * Used for interpolation
         */
        private Vec3 direction;
        
        /**
         * Camera movement speed.
         */
        private int speed;
        
        /**
         * Speed constant.
         */
        private static final int NORMAL_SPEED = 5;
        
        /**
         * Prevents the instantiation of an object of the Camera class
         */
        protected Camera() 
        {
                pos = new Vec3();

                screenH = 0;
                screenW = 0;
                
                minZ = 0;
                maxZ = 0;
                
                minRatio = 1;
                maxRatio = 0;
                
                direction = new Vec3();
                distance = 0;
                speed = NORMAL_SPEED;
        }
        
        /**
         * Gets the singleton instance of the camera. Creates the camera if it's
         * the first time it's called.
         * 
         * TODO: Check speed hit for having a sync
         * 
         * @return the instance of the camera
         */
        public static synchronized Camera Get()
        {
                return instance;
        }
        
        /**
         * It updates the camera. If necessary it interplolates it's movement in a straight line
         * in the this.direction for this.distance.
         */
        public void Update()
        {
                if(HasToMove())
                {
                        double increment = Math.min(this.distance, this.speed);                         
                        this.pos.Offset(this.direction.X()*increment, this.direction.Y()*increment, this.direction.Z()*increment);
                        
                        this.distance -= increment;
                }
        }
        
        /**
         * Initializes the screen size values
         * @param w is the width of the screen
         * @param h is the height of the screen
         */
        public void SetScreenSize(int w, int h)
        {
                this.screenH = h;
                this.screenW = w;
                
                // Set initial z then:
                this.minZ = 2* this.screenH;            
        }
  
        
        /**
         * Called to request the cursor to move to a specified position, in a straight line, to the max of it's speed.
         * @param destination is the point we want the Cursor to translate to
         */
        private void MoveTo(Vec3 destination)
        {
                if(! this.pos.Equals(destination))
                {
                        // move in that direction       
                        MoveInDirection(this.pos.GetVectorTo(destination));
                }
        }
        
        /**
         * Tells the cursor to move in a direction.
         * @param direction Non-unitary vector of the direction we want to move. 
         * The length of the vector is taken to be the length of the path we want
         * to move along. 
         */
        private void MoveInDirection(Vec3 direction)
        {
                this.distance = direction.Length();
                
                this.direction  = direction;
                this.direction.Normalize();             
        }
        
        /**
         * Checks if the Cursor has to move
         * 
         * @return True if it has to move, false if it doesn't.
         */
        private boolean HasToMove()
        {
                if(this.direction == null)
                {
                        return false;
                }
                if(this.distance == 0)
                {
                        return false;
                }
                
                return true;
        }
        
        /**
         * Gets the screen width
         * @return screen width
         */
        public int GetScreenWidth() { return this.screenW; }
        
        /**
         * Gets the screen height
         * @return screen height
         */
        public int GetScreenHeight() { return this.screenH; }
        
        /**
         * Gets the camera x
         * @return x
         */
        public int X() { return (int) this.pos.X(); }
        
        /**
         * Get the camera y
         * @return y
         */
        public int Y() { return (int) this.pos.Y(); }
        
        /**
         * Get the camera z
         * @return z
         */
        public int Z() { return (int) this.pos.Z(); }   
        
        
        /**
         * Gets the camera position vector
         * @return the pos vector
         */
        public Vec3 Position() {return this.pos; }
        
        /**
         * Gets the minimum z
         * @return minZ
         */
        public int GetMinZ() { return this.minZ; }
        
//        /**
//         * Gets the maximum z
//         */
//        public int GetMaxZ() { return 2*Preferences.Get().mapWidth; }

}