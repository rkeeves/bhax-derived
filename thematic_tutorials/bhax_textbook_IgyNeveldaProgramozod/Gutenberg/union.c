typedef struct le_line
{
    int type;
    Le3dPnt  end1;
    Le3dPnt  end2;
} LeLinedata;

typedef struct le_b_spline
{
    int          type;
    int          degree;
    double      *params;
    double      *weights;
    Le3dPnt  *c_pnts;
    int          num_knots;
    int          num_c_points;
} LeBsplinedata;      

typedef struct le_circle
{
    int        type;
    Le3dPnt center;
    Le3dPnt norm_axis_unit_vect;
    double     radius;
} LeCircledata;

typedef union le_curve
{
    LeLinedata         	leline;
    LeBsplinedata      	le_b_spline;
    LeCircledata       	le_circle;
} LeCurvedata;