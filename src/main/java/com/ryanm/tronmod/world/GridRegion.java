package com.ryanm.tronmod.world;

public enum GridRegion {
    CENTRAL_GRID, CIRCUIT_PLAINS, OUTLANDS, DIGITAL_SEA, DATA_STORM, ISO_SANCTUARY, CORRUPTED_EXPANSE, DELETED_SECTOR;

    public static GridRegion at(int x, int z) {
        long dx=x-GridDowntownPlan.CENTER_X,dz=z-GridDowntownPlan.CENTER_Z;
        long distanceSquared=dx*dx+dz*dz;
        if(distanceSquared<768L*768L) return CENTRAL_GRID;
        long cellX=Math.floorDiv(x,512), cellZ=Math.floorDiv(z,512);
        long hash=(cellX*341873128712L+cellZ*132897987541L)^0x5DEECE66DL;
        if(Math.floorMod(hash,47)==0) return ISO_SANCTUARY;
        int boundary=Math.min(Math.floorMod(x,512),Math.floorMod(z,512));
        if(boundary<28) return DATA_STORM;
        double angle=Math.atan2(z,x);
        if(angle>-0.45&&angle<0.45) return DIGITAL_SEA;
        if(angle>0.45&&angle<2.2) return OUTLANDS;
        if(angle>2.2||angle<-2.2) return CORRUPTED_EXPANSE;
        if(angle>-2.2&&angle<-1.0) return DELETED_SECTOR;
        return CIRCUIT_PLAINS;
    }
}
