void f(int i){
    i = 3;
    return ;
}

int main(){
    int a = 1;
    {int c = 2;}
    int b = 5;
    return a;
}