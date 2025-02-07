package tarea1;

import java.util.Calendar;

public class SelectionSort extends Thread{
    
    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
     int[] arreglodeprueba = {5,0,4,7,8,33,6,99,4,70,25,66};
     
    for(int i = 0; i<arreglodeprueba.length; i++){
        int min = i;
        for(int j = i+1; j<arreglodeprueba.length; j++){
            if(arreglodeprueba[min] > arreglodeprueba[j]){
                min = j;
            }
        }
        
        int temp = arreglodeprueba[i];
        arreglodeprueba[i] = arreglodeprueba[min];
        arreglodeprueba[min] = temp;
    }
    
    for(int a = 0; a< arreglodeprueba.length; a++){
        System.out.println(""+ arreglodeprueba[a]);
    }
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
