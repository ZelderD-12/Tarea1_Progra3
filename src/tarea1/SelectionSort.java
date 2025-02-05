package tarea1;

import java.util.Calendar;

public class SelectionSort extends Thread{
    
    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
     int[] arreglodeprueba = {5,0,4,7,8,33,6,99,4};
     for(int a = 0; a<=1000000; a++){
    for(int i = 0; i<= arreglodeprueba.length-1; i++){
        System.out.println("" + arreglodeprueba[i]);
        
    }}
     long endTime = System.currentTimeMillis();
     long milisegundos = endTime - startTime;
     long segundos = 0;
     long minutos = 0;
     long horas = 0;
     
     while(milisegundos >=1000){
         milisegundos-=1000;
         segundos++;
     }
     while(segundos >=60){
         segundos-=60;
         minutos++;
     }
     while(minutos >=60){
         minutos-=60;
         horas++;
     }
        System.out.println("Tiempo transcurrido: " + horas + ":"+ minutos + ":"+ segundos + ":"+ milisegundos);
    };
}
