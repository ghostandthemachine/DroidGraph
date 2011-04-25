package com.android.droidgraph.util;


import android.util.Log;


public class Vec3 
{
        /**
         * x coordinate of the vector
         */
        private double x;
        
        /**
         * y coordinate of the vector
         */
        private double y;
        
        /**
         * z coordinate of the vector
         */
        private double z;
        
        /**
         * Empty constructor, initializes to 0
         */
        public Vec3()
        {
                this.x = 0;
                this.y = 0;
                this.z = 0;
        }
        
        /**
         * Creates an instance of the Vec2 class and initializes x and y
         * @param x
         * @param y
         */
        public Vec3(double x, double y, double z)
        {
                this.x = x;
                this.y = y;
                this.z = z;
        }

        /**
         * Calculates if this Vec2 is equal to the one provided
         * @param v is the Vec2 to compare against
         * @return True if it's equal, false if it isn't
         */
        public boolean Equals(Vec3 v)
        {
                if(this.x == v.x && this.y == v.y && this.z == v.z) return true;
                else return false;
        }
        
        /**
         * Calculates the vector length
         * @return the length of the vector
         */
        public double Length()
        {
                return Math.sqrt((this.x*this.x + this.y*this.y + this.z*this.z));
        }
        
        /**
         * Normalizes the vector
         */
        public void Normalize()
        {
                double len = Length();
                this.x /= len;
                this.y /= len;
                this.z /= len;
        }
        
        /**
         * Returns a vector from this to the point provided
         * @param point to calculate the vector to
         * @return The vector.
         */
        public Vec3 GetVectorTo(Vec3 point)
        {
                Vec3 aux = new Vec3();
                
                aux.SetX(point.x - this.x);
                aux.SetY(point.y - this.y);
                aux.SetZ(point.z - this.z);
                
                return aux;
        }
        
        /**
         * Sets the x,y
         * @param x
         * @param y
         */
        public void Set(double x, double y, double z)
        {
                this.x = x;
                this.y = y;
                this.z = z;
        }
        
        /**
         * Sets the x
         * @param x
         */
        public void SetX(double x)
        {
                this.x = x;
        }
        
        /**
         * Sets the y
         * @param y
         */
        public void SetY(double y)
        {
                this.y = y;
        }
        
        /**
         * Sets the z
         * @param z
         */
        public void SetZ(double z)
        {
                this.z = z;
        }
        
        /**
         * Adds the offset to the current position
         * @param x to add to the x component
         * @param y to add to the y component
         */
        public void Offset(double x, double y, double z)
        {
                this.x += x;
                this.y += y;
                this.z += z;
        }
        
        /**
         * Gets the x value
         * @return x
         */
        public double X() { return this.x; }
        
        /**
         * Gets the y value
         * @return y
         */
        public double Y() { return this.y; }
        
        /**
         * Gets the z value
         * @return z
         */
        public double Z() { return this.z; }
        
        /**
         * Prints the vector value to the log
         */
        public void Print(String tag, String msg)
        {
                Log.i(tag, msg + ": " + this.x + ", " + this.y + ", " + this.z);
        }

}