#include <iostream>
#include <cmath>
#include <string>
using namespace std;

int length(int n) {
    int l = 1;
    if (n < 0) {
        l++;
        n = -n;
    }
    for(; n/=10; ++l);
    return l;
    }

class Point
{
	public:
		double x, y, z;
		double r();
		Point(double x, double y, double z);
        void Print();
		virtual ~Point();
};

Point :: Point (double x, double y, double z)
{
 this->x = x;
 this->y = y;
 this->z = z;
 cout << "("<< x <<","<< y <<","<< z <<")" << endl;
}

void Point :: Print()
{
 cout << "("<< x <<","<< y <<","<< z <<")";
}

Point ::~Point()
{
 cout << "Point " << "("<< x <<","<< y <<","<< z <<")" << " selfdestructed!!!!" << endl;
}

double Point :: r()
{
 return sqrt(x*x+y*y+z*z);
}


class Universe
{
    public:
        int n;
        Point** p;
        Universe();
        void Line(int p1, int p2);
        virtual ~Universe();

};

Universe :: Universe()
{
    this->n = 0;
    this->p = new Point* [20];
}

Universe :: ~Universe()
{
    while (n > 0)
    {
        delete p[--n];
    }
    delete [] p;
    cout << "Universe deleted" << endl;
}

void Universe :: Line(int p1, int p2)
{
    cout << "Line by points ";
    p[p1]->Print();
    cout << " and ";
    p[p2]->Print();
    cout << endl;
    cout << "x - " << p[p1]->x << "   " << "y - " << p[p1]->y << "   " << "z - " << p[p1]->z << endl;
    int ln1 = 4 + length(p[p1]->x);
    int ln2 = 4 + length(p[p1]->y);
    int ln3 = 4 + length(p[p1]->z);
    cout << string(ln1, '-') << " = " << string(ln2, '-') << " = " << string(ln3, '-') <<endl;
    int t1 = p[p2]->x - p[p1]->x;
    int t2 = p[p2]->y - p[p1]->y;
    int t3 = p[p2]->z - p[p1]->z;
    cout <<  string(ln1/2, ' ') << t1 << string(ln1/2 - length(t1) + 3, ' ')
    <<  string(ln2/2, ' ') << t2 << string(ln2/2 - length(t2) + 3, ' ')
    <<  string(ln3/2, ' ') << t3 << string(ln3/2 - length(t3), ' ') << endl;
}

int main()
{
    Universe* U = new Universe();

    for (double i=0; i<10; i++)
        {
            Point* A = new Point(i,i*i,i*i+i);
            cout << "dist: " << A->r() << endl;
            U->p[U->n++] = A;
        }

    U->Line(4, 3);
    delete U;
    return 0;
}
