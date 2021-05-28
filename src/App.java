import java.util.*; 
import java.sql.*;
import java.sql.DriverManager;
//------------------------------------------------------------FCFS--------------------------------------------------------

class FCFS{
static void findWaitingTime(int processes[], int n, int bt[], int wt[], int at[]) //This function calculates the waiting time for all the processes
{ 
	int st[] = new int[n]; //service_time // Service time means amount of time after which a process can start execution. It is summation of burst time of previous processes
	st[0] = 0; 
	wt[0] = 0; 
    for (int i = 1; i < n ; i++)    //calculation of waiting time  //runs for 2nd process to last process
	{ 
		//representing wasted time in queue
		int wasted=0;
		// Add burst time of previous processes 
		st[i] = st[i-1] + bt[i-1];   //formula for calculating service time
		wt[i] = st[i] - at[i];  //waiting time of current proccess "i". ((bt[1]+bt[2]=.........bt[i-1]  - at[i]))
		if (wt[i] < 0)     // If waiting time for a process is in negative, that means it is already in the ready queue before CPU becomes idle so its waiting time is 0. wasted time is basically time for process to wait after a process is over
		{
			wasted = Math.abs(wt[i]);
			wt[i] = 0; 
		}
		st[i] = st[i] + wasted;
	} 
} 
static void findTurnAroundTime(int processes[], int n, int bt[],int wt[], int tat[]) // Function to calculate turn around time 
{
	for (int i = 0; i < n ; i++)  // Calculating turnaround time by adding bt[i] + wt[i] 
		tat[i] = bt[i] + wt[i]; 
} 
static void findavgTime(int processes[], int n, int bt[], int at[])  // Function to calculate average waiting and turn-around 
{ 
	int wt[] = new int[n], tat[] = new int[n];    //array of waiting time and turnaround time has been made here
	findWaitingTime(processes, n, bt, wt, at); // Function to find waiting time of all processes 
	findTurnAroundTime(processes, n, bt, wt, tat); // Function to find turn around time for all processes 
	
	// Display processes along with all details 
	System.out.print("Processes " + " Burst Time " + " Arrival Time "+ " Waiting Time " + " Turn-Around Time "+ " Completion Time \n"); 
	int total_wt = 0, total_tat = 0; 
	for (int i = 0 ; i < n ; i++) 
	{ 
		total_wt = total_wt + wt[i]; 
		total_tat = total_tat + tat[i]; 
		int compl_time = tat[i] + at[i]; 
		System.out.println(i+1 + "\t\t" + bt[i] + "\t\t"+ at[i] + "\t\t" + wt[i] + "\t\t "+ tat[i] + "\t\t " + compl_time); 
	} 
    System.out.print("Average waiting time = "
		+ (float)total_wt / (float)n); 
	System.out.print("\nAverage turn around time = "
		+ (float)total_tat / (float)n+"\n"); 
} 

} 



//------------------------------------------------------------ROUND ROBIN-------------------------------------------------

class RoundRobin{
	public static void findWaitingTime(int process[],int wt_time[],int n ,int brusttime[],int quantum,int completion_time[],int arrival_time[])
	{
		
		int rem_time[] = new int[n];     //it is the array which keeps on updating for remaining burst time for each procceses
		
		for(int i=0;i<wt_time.length;i++)
		{
			rem_time[i]= brusttime[i];                //At first remaining time will be equal to burst time
		}
		int t=0;
		int arrival=0;
		while(true)          //This part of code helps in scheduling the processes to the CPU.
        {
			boolean done = true;
			for(int i=0;i<n;i++)
			{
				if(rem_time[i]>0)         //this condition checks whether the process has been fully executed or not. If not it changes done to false
				{
					done =false;           
					if(rem_time[i]>quantum && arrival_time[i]<=arrival)
					{
						t +=quantum;
						rem_time[i]-=quantum;
						arrival++;
					}
					else{
					if(arrival_time[i]<=arrival)
					{
						arrival++;
						t+=rem_time[i];
						rem_time[i]=0;
						completion_time[i]=t; 
					}
					}
				}
			}
			
			if(done==true)             //this condition checks whether the process has been fully executed or not. If yes it changes done to true for that particular process
			{ 
				break;
			}
		} 
	}
	public static void findTurnAroundTime(int process[] ,int wt_time[],int n,int brusttime[],int tat_time[],int completion_time[],int arrival_time[])
	{
		for(int i=0;i<n;i++)
		{
			tat_time[i]= completion_time[i]-arrival_time[i];  //formula for calculating tat for each process
			wt_time[i] = tat_time[i]-brusttime[i];        //formula for calculating the waiting time of all the proccesses
		}	
	}
	public static void findAvgTime(int process[],int n,int brusttime[],int quantum,int arrival_time[]){
	int wt_time[] = new int[n];   //array of waiting time
	int tat_time[] = new int[n];     //array of turnaround time
	int completion_time[] = new int[n];     //array of completion time
	findWaitingTime(process,wt_time,n,brusttime,quantum,completion_time,arrival_time);   //n here is no. of processes
	findTurnAroundTime(process,wt_time,n,brusttime,tat_time,completion_time,arrival_time);
	int total_wt = 0, total_tat = 0; 
	
	System.out.println("Processes " +" Arrival Time\t"+ " Burst time " +" completion time"+ 
			" Turn Around Time " + " Waiting time");
	for (int i=0; i<n; i++) 
	{ 
		total_wt = total_wt + wt_time[i]; 
		total_tat = total_tat + tat_time[i]; 
		System.out.println(" " + (i+1) + "\t\t"+ arrival_time[i]+"\t\t"+ + brusttime[i] +"\t " +completion_time[i]+"\t\t"
							+tat_time[i] +"\t\t " + wt_time[i]); 
	} 
	
	System.out.println("Average waiting time = " + 
						(float)total_wt / (float)n); 
	System.out.println("Average turn around time = " + 
						(float)total_tat / (float)n); 
	}

}


//------------------------------------------------------------SRTF--------------------------------------------------------

class Process
{ 
   int ProcessId, bt, at;    //process id, burst time, arrival time 
   public Process(int ProcessId, int bt, int at) 
   { 
      this.ProcessId = ProcessId; 
      this.bt = bt; 
      this.at = at; 
   } 
}

class SRTF           //this method finds the waiting time for all the processes 
{
   static void findWaitingTime(Process processes[], int n, int wt[])    //wt is waiting time 
   {									
	int rt[] = new int[n];     //remaining time, allocating memory to array rt 
	// Copy the burst time into rt[] 
        for (int i = 0; i < n; i++) 
	     rt[i] = processes[i].bt; 
	int complete = 0, t = 0, minm = Integer.MAX_VALUE;   //t maintains time lapse,i.e, every second eg: 1,2,3....
	int shortest = 0, finish_time; // shortest tells about the shortest process and finish_time is the time at which all the processes ends 
	boolean check = false; 
	
        //it will run until all processes are complete 
	while (complete != n) 
        {     
              //it find process will least remaining time at every lapse 
	      for (int j = 0; j < n; j++)    //j is counter for number of processes 
       	      {   
                  //arrival time of the process should be less than the current 
                  //time and remaining time should be greater than 0 but less than max value 
		  if ((processes[j].at <= t) && (rt[j] < minm) && rt[j] > 0)   
                  { 
		      minm = rt[j]; 
		      shortest = j; 
		      check = true; 
		   }		 
       	      }
	      if (check == false) 
              { 
		  t++; 
		  continue; 
	      } 
	      
	      rt[shortest]--;         // Reduce remaining time by one 
	      minm = rt[shortest];   // Update minimum 
	      if (minm == 0) 
		  minm = Integer.MAX_VALUE; 
	
	      // If a process gets completely executed 
	      if (rt[shortest] == 0) 
              { 
		  complete++;      //Increment complete 
		  check = false; 
	
		  // Find finish time of current process 
		  finish_time = t + 1;   //t+1 because t started from 0 
           
                 // Calculate waiting time 
		  wt[shortest] = finish_time - processes[shortest].bt - processes[shortest].at;
		  if (wt[shortest] < 0) 
		      wt[shortest] = 0; 
	       } 
		  t++;   //increment timer 
	} 
    }
	// Method to calculate turn around time 
    static void findTurnAroundTime(Process processes[], int n, int wt[], int tat[]) 
    { 
	for (int i = 0; i < n; i++) 
	     tat[i] = processes[i].bt + wt[i]; 
    } 
	
	// Method to calculate average time 
     static void findavgTime(Process processes[], int n) 
     { 
	int wt[] = new int[n], tat[] = new int[n]; 
	int total_wt = 0, total_tat = 0; 
	
	// Function to find waiting time of all processes
	findWaitingTime(processes, n, wt); 
	
	// Function to find turn around time for all processes 
	findTurnAroundTime(processes, n, wt, tat); 
		
        System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time"); 
	
	// Calculating total waiting time and total turnaround time 
	for (int i = 0; i < n; i++) 
        { 
            total_wt = total_wt + wt[i]; 
	    total_tat = total_tat + tat[i]; 
	    System.out.println(" " + processes[i].ProcessId + "\t\t" + processes[i].bt + "\t\t " + wt[i] + "\t\t" + tat[i]); 
	} 
	
	System.out.println("Average waiting time = " + (float)total_wt / (float)n); 
	System.out.println("Average turn around time = " + (float)total_tat / (float)n); 
      } 
} 

//---------------------------------------------------------MENU STARTS---------------------------------------------------------
class Menu
{
   public static void main(String[] args)
   {
    Scanner scan = new Scanner(System.in);
	System.out.println("\n\n\n");
	System.out.println("\n-------------------------------------");
	System.out.println("| Press 1 To Enter Knowledge Center |");
	System.out.println("| Press 2 To Enter Analysis Center  |");
	System.out.println("-------------------------------------\n");

	int choice5 = scan.nextInt();
	if(choice5 == 1)
	{
	Connection con = null;
	Scanner sc = new Scanner(System.in);
	int choice1;
	try
	{
		con = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/operatingsystem","root","Ddhruv@1234");
		if(con!=null)
		{
			System.out.println("Connection sucesful\n");
		}
		while(true)
	{
		System.out.println("\n----------------------------------------------------------------");
        System.out.println("| WELCOME TO KNOWLEDGE CENTER                                  |");
        System.out.println("| Press 1 to know about First Come First Serve Algorithm       |");
        System.out.println("| Press 2 to know about Round RobinAlgorithm                   |");
        System.out.println("| Press 3 to know about Shortest Job First Algorithm           |");
        System.out.println("| Press 4 to know about Shortest Remaining Time First          |");
        System.out.println("| Press 5 to know about Priority Scheduling Algorithm          |");
		System.out.println("----------------------------------------------------------------\n");
		choice1 = sc.nextInt();
		ResultSet rs;
		Statement stmt = con.createStatement();
		switch(choice1)
	   {
		 case 1:
		 rs = stmt.executeQuery("SELECT * FROM algorithms WHERE `No.` = '1'");;
		 while (rs.next()) 
		{
		 System.out.println("\n\n\n\n----------------------------------------------------------------\n") ;  
		 // Retrieve by column name
		 System.out.print("No.: " + rs.getInt("No.")+"\n");
		 System.out.print("Name: " + rs.getString("Algorithm_Name")+"\n");
		 System.out.print("Description: " + rs.getString("Description")+"\n");
		 System.out.println("Links: " + rs.getString("Links")+"\n");
		 System.out.println("\n----------------------------------------------------------------\n") ;  

		}
		break;

		case 2:
		rs = stmt.executeQuery("SELECT * FROM algorithms WHERE `No.` = '2'");;
		while (rs.next()) 
		{
		 System.out.println("\n\n\n\n----------------------------------------------------------------\n") ;  
		 // Retrieve by column name
		 System.out.print("No.: " + rs.getInt("No.")+"\n");
		 System.out.print("Name: " + rs.getString("Algorithm_Name")+"\n");
		 System.out.print("Description: " + rs.getString("Description")+"\n");
		 System.out.println("Links: " + rs.getString("Links")+"\n");
		 System.out.println("\n----------------------------------------------------------------\n") ;  

		}
		break;

		case 3:
		rs = stmt.executeQuery("SELECT * FROM algorithms WHERE `No.` = '3'");;
		while (rs.next()) 
		{
		 System.out.println("\n\n\n\n----------------------------------------------------------------\n") ;  
		 // Retrieve by column name
		 System.out.print("No.: " + rs.getInt("No.")+"\n");
		 System.out.print("Name: " + rs.getString("Algorithm_Name")+"\n");
		 System.out.print("Description: " + rs.getString("Description")+"\n");
		 System.out.println("Links: " + rs.getString("Links")+"\n");
		 System.out.println("\n----------------------------------------------------------------\n") ;  

		}
		break;

		case 4:
		rs = stmt.executeQuery("SELECT * FROM algorithms WHERE `No.` = '4'");;
		while (rs.next()) 
		{
		 System.out.println("\n\n\n\n----------------------------------------------------------------\n") ;  
		 // Retrieve by column name
		 System.out.print("No.: " + rs.getInt("No.")+"\n");
		 System.out.print("Name: " + rs.getString("Algorithm_Name")+"\n");
		 System.out.print("Description: " + rs.getString("Description")+"\n");
		 System.out.println("Links: " + rs.getString("Links")+"\n");
		 System.out.println("\n----------------------------------------------------------------\n") ;  

		}
		break;

		case 5:
		rs = stmt.executeQuery("SELECT * FROM algorithms WHERE `No.` = '5'");;
		while (rs.next()) 
		{
		 System.out.println("\n\n\n\n----------------------------------------------------------------\n") ;  
		 // Retrieve by column name
		 System.out.print("No.: " + rs.getInt("No.")+"\n");
		 System.out.print("Name: " + rs.getString("Algorithm_Name")+"\n");
		 System.out.print("Description: " + rs.getString("Description")+"\n");
		 System.out.println("Links: " + rs.getString("Links")+"\n");
		 System.out.println("\n----------------------------------------------------------------\n") ;  

		}
		break;

		default:
		System.out.println("Invalid Choice\n");
		break;
	 }
	}
 }
	catch(Exception e)
	{
		System.out.println("Connection Unsuccessful\n"+e.getMessage());
	}
}
    else 
       {
       int choice; 
       Scanner input = new Scanner(System.in); 
       System.out.println("Enter the choice of algorithm");
       while(true)
       {
         System.out.println("1. First Come First Serve (FCFS)"); 
         System.out.println("2. Round Robin (RR)");
         System.out.println("3. Shortest Job First (SJF)");
	     System.out.println("4. Shortest Remaining Time First (SRTF)");
	     System.out.println("5. Priority Scheduling (PS)"); 
         System.out.println("6. Exit!"); 
         

	 choice = input.nextInt(); 

	 switch(choice) 
	 {

	   case 1: 
	    Scanner sc1 = new Scanner(System.in);
	    int p1;
            char yn;    // to add more processes 
            int newp;   // Process ids in new array 
            int merge_p;     // length of merged array
         // Process id's 
     	System.out.println("Enter no. of Processes\n");
	    p1 = sc1.nextInt();

        int processes1[] = new int[p1];  //contains process id's
        int burst_time1[] = new int[p1];  //it will contain the burst time for processes
	    int arrival_time1[] = new int[p1];  //it will contain the arrival time of processes
		System.out.println("Enter burst time for Processes\n");
	    for(int i=0;i<p1;i++)
	   {
		burst_time1[i] = sc1.nextInt();
	   }

        System.out.println("Enter arrival time for Processes\n");
	    for(int i=0;i<p1;i++)
	    {
		 arrival_time1[i] = sc1.nextInt();
	    }
		FirstComeFirstServe(processes1,p1,burst_time1,arrival_time1);  //calling of FCFS function, p1 here is no. of processes
	   

            System.out.println("Do you want to add more processes? Y/N"); 
            yn = sc1.next().charAt(0); 

            if(yn == 'Y') 
           { 
              System.out.println("How many processes do you want to add?"); 
              newp = sc1.nextInt(); 
      
              int new_processes[] = new int[newp]; 
              int new_burst_time[] = new int[newp]; 
              int new_arrival_time[] = new int[newp];  


              System.out.println("Enter burst time for new Processes\n"); 
              for(int i=0; i<newp; i++)
             {    new_burst_time[i] = sc1.nextInt(); }   
 
             System.out.println("Enter arrival time for new Processes\n");  
             for(int i=0; i<newp; i++)
            {    new_arrival_time[i] = sc1.nextInt(); }                                                   
                                                                                                  
            //merging the two arrays 

             merge_p = p1 + newp;  //length of the merged array 
    
           //merged arrays 

           int merge_processes[] = new int[merge_p];      
           int merge_burst_time[] = new int[merge_p]; 
           int merge_arrival_time[] = new int[merge_p]; 

           //using arraycopy function to copy values of both arrays into the merged array 
     
            //merging processes 
            System.arraycopy(processes1, 0, merge_processes, 0, p1); //process1 is src array, 0 is the starting pos of source array, merge_process is the destination array, 0 is the starting pos of the dest array, p1 is the no. of elements to b copied
            System.arraycopy(new_processes, 0, merge_processes, p1, newp); //merged


           //merging burst time
            System.arraycopy(burst_time1, 0, merge_burst_time, 0, p1);
            System.arraycopy(new_burst_time, 0, merge_burst_time, p1, newp); 



           //merging arrival time 
            System.arraycopy(arrival_time1, 0, merge_arrival_time, 0, p1);
            System.arraycopy(new_arrival_time, 0, merge_arrival_time, p1, newp);

           //finding average time with new set of processes
           FCFS.findavgTime(merge_processes, merge_p, merge_burst_time, merge_arrival_time); 
     
         }  //end of if 

        break; 

	   case 2:
	      Scanner sc2 = new Scanner(System.in);
              int quantum = 2;   //taken as fixed
              int p2;
              System.out.println("Enter no. of Processes\n");
              p2 = sc2.nextInt();
              int processes2[] = new int[p2];   //contains process id's
              int burst_time2[] = new int[p2];  //it will contain the burst time for processes
              int arrival_time2[] = new int[p2];    //it will contain the arrival time for processes
              System.out.println("Enter burst time for Processes\n");
              for(int i=0;i<p2;i++)
              {
                 burst_time2[i] = sc2.nextInt();
              }
  
	      System.out.println("Enter arrival time for Processes\n");
              for(int i=0;i<p2;i++)
              {
                 arrival_time2[i] = sc2.nextInt();
              }
			RoundRobin(processes2,p2,burst_time2,arrival_time2);  //Calling of RoundRobin function. p2 here is no. of processes.	

              System.out.println("Do you want to add more processes? Y/N"); 
              yn = sc2.next().charAt(0); 

              if(yn == 'Y') 
             { 
                System.out.println("How many processes do you want to add?"); 
                newp = sc2.nextInt(); 
      
                int new_processes[] = new int[newp]; 
                int new_burst_time[] = new int[newp]; 
                int new_arrival_time[] = new int[newp];  


                System.out.println("Enter burst time for new Processes\n"); 
                for(int i=0; i<newp; i++)
                {    new_burst_time[i] = sc2.nextInt(); }   
           
                System.out.println("Enter arrival time for new Processes\n");  
                for(int i=0; i<newp; i++)
                {    new_arrival_time[i] = sc2.nextInt(); }                                                  // System.arraycopy(a, 0, c, 0, a1);
                                                                                                  // System.arraycopy(b, 0, c, a1, b1);

                //merging the two arrays 

                merge_p = p2 + newp;  //length of the merged array 
    
               //merged arrays 

               int merge_processes[] = new int[merge_p];      
               int merge_burst_time[] = new int[merge_p]; 
               int merge_arrival_time[] = new int[merge_p]; 

               //using arraycopy function to copy values of both arrays into the merged array 
     
              //merging processes 
                System.arraycopy(processes2, 0, merge_processes, 0, p2);
                System.arraycopy(new_processes, 0, merge_processes, p2, newp); 

              //merging burst time
                System.arraycopy(burst_time2, 0, merge_burst_time, 0, p2);
                System.arraycopy(new_burst_time, 0, merge_burst_time, p2, newp); 

             //merging arrival time 
                System.arraycopy(arrival_time2, 0, merge_arrival_time, 0, p2);
                System.arraycopy(new_arrival_time, 0, merge_arrival_time, p2, newp);

             //finding average time with new set of processes
               RoundRobin.findAvgTime(merge_processes, merge_p, merge_burst_time, quantum, merge_arrival_time); 
     
          }  //end of if 

            break; 
                      
           
        case 3: 
		Scanner AB=new Scanner(System.in);
		System.out.println ("Enter no. of Processes\n");
		int n= AB.nextInt();
		int process[] = new int[n]; 
		int arrival[] = new int[n]; 
		int burest[] = new int[n]; 
		int burestcopy[] = new int[n]; 

	    for (int i=0;i<n;i++)
	    {
	    	process[i]= i+1;
	    	System.out.println ("Enter arrival time of each process\n");
	    	arrival[i]= AB.nextInt();
	    	System.out.println("Enter burst time of each process\n");
	    	burest[i]= AB.nextInt();
	    }
		    Sjf(process,n,burest,arrival);

             System.out.println("Do you want to add more processes? Y/N");
    	     yn = AB.next().charAt(0); 

             if(yn == 'Y') 
             { 
                System.out.println("How many processes do you want to add?"); 
                newp = AB.nextInt(); 
                int burestcopy1[] = new int[]{1,4,3}; 
                int new_processes[] = new int[newp]; 
                int new_burst_time[] = new int[newp]; 
                int new_arrival_time[] = new int[newp];  

                
        for (int i=0;i<newp;i++)
	    {
	    	new_processes[i]= i+1;
	    	System.out.println ("Enter arrival time of each process\n");
	    	new_arrival_time[i]= AB.nextInt();
	    	System.out.println("Enter burst time of each process\n");
	    	new_burst_time[i]= AB.nextInt();
	    }
		System.out.println("HEYYYYYYYY\n");

              /*  System.out.println("Enter burst time for new Processes\n"); 
                for(int i=0; i<newp; i++)
                {    new_burst_time[i] = AB.nextInt(); }   
 
                System.out.println("Enter arrival time for new Processes\n");  
                for(int i=0; i<newp; i++)
               {    new_arrival_time[i] = AB.nextInt(); }        */                                          
                                                                                                  

                //merging the two arrays 

                merge_p = n + newp;  //length of the merged array 
				System.out.println("HEYYYYYYYYYOOOOOOOO\n");

    
               //merged arrays 

               int merge_processes[] = new int[merge_p];      
   	       int merge_burst_time[] = new int[merge_p]; 
               int merge_arrival_time[] = new int[merge_p]; 


               //using arraycopy function to copy values of both arrays into the merged array 
     
              //merging processes 
                System.arraycopy(process, 0, merge_processes, 0, n);
                System.arraycopy(new_processes, 0, merge_processes, n, newp);
		
				
              //merging burst time
                System.arraycopy(burestcopy1, 0, merge_burst_time, 0, n);
	            System.arraycopy(new_burst_time, 0, merge_burst_time, n, newp); 

             //merging arrival time 
                System.arraycopy(arrival, 0, merge_arrival_time, 0, n);
                System.arraycopy(new_arrival_time, 0, merge_arrival_time, n, newp);

             //finding average time with new set of processes
                Sjf(merge_processes,merge_p,merge_burst_time,merge_arrival_time);
				 
    // findAvgTime (int n, int process[], int arrival[], int burest[])
    // Sjf(process,n,burest,arrival);
           }  //end of if 
         break;

	   case 4:
	   System.out.println("Enter no. of Processes\n");
	   Scanner scann = new Scanner(System.in);
	   int p = scann.nextInt();
	   Process[] proc = new Process[p];
	   System.out.println("--------\n");
	   int art[] = new int[p];
	   int brt[] = new int[p];
	   System.out.println("Enter Arrival time for Each process\n");
	   for(int i=0;i<p;i++)
	   {
		   art[i] = scann.nextInt();
	   }
	   System.out.println("Enter Burst time for Each process\n");
	   for(int i=0;i<p;i++)
	   {
		   brt[i] = scann.nextInt();
	   }
	   for(int i=0;i<p;i++)
	   {
		   proc[i] = new Process(i+1,brt[i],art[i]);
	   }
        Srtf(proc,p); 

            
            System.out.println("Do you want to add more processes? Y/N"); 
            yn = scann.next().charAt(0); 

            if(yn == 'Y') 
           { 
              System.out.println("How many processes do you want to add?"); 
              newp = scann.nextInt(); 
      
             Process[] new_processes = new Process[newp]; 
             int new_burst_time[] = new int[newp]; 
             int new_arrival_time[] = new int[newp];  


             System.out.println("Enter burst time for new Processes\n"); 
             for(int i=0; i<newp; i++)
             {    new_burst_time[i] = scann.nextInt(); }   
 
             System.out.println("Enter arrival time for new Processes\n");  
             for(int i=0; i<newp; i++)
             {    new_arrival_time[i] = scann.nextInt(); }       
             
             for(int i=0;i<newp;i++)
	     {
		  new_processes[i] = new Process(i+1,new_burst_time[i],new_arrival_time[i]);
	     }                                           
                                                                                                  

            //merging the two arrays 

            merge_p = p + newp;  //length of the merged array 
    
            //merged arrays 

             Process[] merge_processes = new Process[merge_p];      
             int merge_burst_time[] = new int[merge_p]; 
             int merge_arrival_time[] = new int[merge_p]; 

           //using arraycopy function to copy values of both arrays into the merged array 
     
          //merging processes 
             System.arraycopy(proc, 0, merge_processes, 0, p);
             System.arraycopy(new_processes, 0, merge_processes, p, newp); 

         //merging burst time
             System.arraycopy(brt, 0, merge_burst_time, 0, p);
             System.arraycopy(new_burst_time, 0, merge_burst_time, p, newp); 

         //merging arrival time 
             System.arraycopy(art, 0, merge_arrival_time, 0, p);
             System.arraycopy(new_arrival_time, 0, merge_arrival_time, p, newp);

        //finding average time with new set of processes
            SRTF.findavgTime(merge_processes, merge_p); 
     
   }  //end of if 

            break; 
           
	   case 5: 
	    int burstTime[];
	   int priority[];
	   int arrivalTime[];
	   String[] processId;
	   int numberOfProcess;
	   Scanner sc5 = new Scanner(System.in);
	   System.out.print("Enter total no. of Procceses\n");
	   int inputNumberOfProcess = sc5.nextInt();
		   numberOfProcess = inputNumberOfProcess;
		   burstTime = new int[numberOfProcess];
		   priority = new int[numberOfProcess];
		   arrivalTime = new int[numberOfProcess];
		   processId = new String[numberOfProcess];
		   String st = "P";
		   for (int i = 0; i < numberOfProcess; i++) 
		   {
			   processId[i] = st.concat(Integer.toString(i));
			   System.out.print("Enter the burst time   for Process - " + (i) + " : ");
			   burstTime[i] = sc5.nextInt();
			   System.out.print("Enter the arrival time for Process - " + (i) + " : ");
			   arrivalTime[i] = sc5.nextInt();
			   System.out.print("Enter the priority     for Process - " + (i) + " : ");
			   priority[i] = sc5.nextInt();
		   }
		    Priority(processId,numberOfProcess,burstTime,arrivalTime,priority);

               System.out.println("Do you want to add more processes? Y/N"); 
               yn = sc5.next().charAt(0); 

               if(yn == 'Y') 
              { 
                  System.out.println("How many processes do you want to add?"); 
                  newp = sc5.nextInt(); 

                   int new_burstTime[] = new int[newp];
		   int new_priority[] = new int[newp];
		   int new_arrivalTime[] = new int[newp];
		   String new_processId[] = new String[newp];

                   String st1 = "P";
		   for (int i = 0 ; i < newp; i++) 
		   {
			   new_processId[i] = st1.concat(Integer.toString(i));
			   System.out.print("Enter the burst time   for Process - " + (i) + " : ");
			   new_burstTime[i] = sc5.nextInt();
			   System.out.print("Enter the arrival time for Process - " + (i) + " : ");
			   new_arrivalTime[i] = sc5.nextInt();
			   System.out.print("Enter the priority     for Process - " + (i) + " : ");
			   new_priority[i] = sc5.nextInt();
		   }
      

     		//merging the two arrays 

    		 merge_p = numberOfProcess + newp;  //length of the merged array 
    
    		 //merged arrays 

     		String merge_processes[] = new String[merge_p];      
     		int merge_burst_time[] = new int[merge_p]; 
   		int merge_arrival_time[] = new int[merge_p]; 
                int merge_priority[] = new int[merge_p];
    		 //using arraycopy function to copy values of both arrays into the merged array 
     
    		 //merging processId
    		 System.arraycopy(processId, 0, merge_processes, 0, numberOfProcess);
    		 System.arraycopy(new_processId, 0, merge_processes, numberOfProcess, newp); 

     		//merging burst time
    		 System.arraycopy(burstTime, 0, merge_burst_time, 0, numberOfProcess);
    		 System.arraycopy(new_burstTime, 0, merge_burst_time, numberOfProcess, newp); 

    		//merging arrival time 
    		System.arraycopy(arrivalTime, 0, merge_arrival_time, 0, numberOfProcess);
    		System.arraycopy(new_arrivalTime, 0, merge_arrival_time, numberOfProcess, newp);
 
                 //merging priority 
    		 System.arraycopy(priority, 0, merge_priority, 0, numberOfProcess);
    		 System.arraycopy(new_priority, 0, merge_priority, numberOfProcess, newp);


   		 //finding average time with new set of processes
     		 Priority(merge_processes, merge_p, merge_burst_time, merge_arrival_time, merge_priority); 
     
              }  //end of if 
	        break;
	
   
            case 6: System.out.println("Closing the application!"); 
                    System.exit(0); 
                    break; 

	    default: System.out.println("Invalid Choice. Please re-enter your choice."); 

        } 
    }
		}
	}

public static void FirstComeFirstServe(int proc[],int p1,int brt[],int art[])     /*-----------------------------------FCFS Function------------------------------*/
{
	FCFS.findavgTime(proc, p1,brt,art);
}

public static void RoundRobin(int processes2[],int p2,int burst_time2[],int arrival_time2[])                /*-----------------------------------RR Function------------------------------*/
{
	int quantum = 2;   //taken as fixed
RoundRobin.findAvgTime(processes2,p2,burst_time2,quantum,arrival_time2);     //findAvgTime is static function, so it can be called by class name
}

public static void Sjf(int process[],int n,int burest12[],int arrival[])                     /*-----------------------------------SJF Function------------------------------*/
{
	int ct[] = new int[n]; 
		int turn[] = new int[n];
		int wt[] = new int[n];  
		int f[] = new int[n];  
		int k[]= new int[n];   
	    int i, st=0, tot=0;
	    float avgwt=0, avgta=0;
		for(int j=0;j<n;j++)
		{
			k[j]= burest12[j];
			f[j] = 0;
		}
	while(true)
		{
	    	int min=1000,c=n;
	    	if (tot==n)
	    		break;
	    	
	    	for (int zk=0;zk<n;zk++)
	    	{
	    		if ((arrival[zk]<=st) && (f[zk]==0) && (burest12[zk]<min))
	    		{	
	    			min=burest12[zk];
	    			c=zk;
	    		}
	    	}
	    	
	    	if (c==n)
	    		st++;
	    	else
	    	{
	    		burest12[c]--;
	    		st++;
	    		if (burest12[c]==0)
	    		{
	    			ct[c]= st;
	    			f[c]=1;
	    			tot++;
	    		}
	    	}
	    }
	    
	    for(i=0;i<n;i++)
	    {
	    	turn[i] = ct[i] - arrival[i];
	    	wt[i] = turn[i] - k[i];
	    	avgwt+= wt[i];
	    	avgta+= turn[i];
	    }
	    System.out.println("process id  arrival  burst  complete turn waiting");
	    
	    for(i=0;i<n;i++)
	    {
	    	System.out.println(process[i] +"       \t"+ arrival[i]+"\t"+ k[i] +"\t"+ ct[i] +"\t"+ turn[i] +"\t"+ wt[i]);
	    }

	    
	    System.out.println("\naverage tat is "+ (float)(avgta/n));
	    System.out.println("average wt is "+ (float)(avgwt/n));
}

public static void Srtf(Process proc[],int p)                /*-----------------------------------SRTF Function------------------------------*/
{
	   SRTF.findavgTime(proc,p);
}

public static void Priority(String processId[],int numberOfProcess,int burstTime[],int arrivalTime[],int priority[])                                      /*-----------------------------------Priority Function------------------------------*/
{
	
		int finishTime[] = new int[numberOfProcess];
	int bt[] = burstTime.clone();
	int at[] = arrivalTime.clone();
	int prt[] = priority.clone();
	String pid[] = processId.clone();
	int waitingTime[] = new int[numberOfProcess];   //at, bt, prt, pid
	int turnAroundTime[] = new int[numberOfProcess];

	int[] at1 = at;
	int[] bt1 = bt;
	int[] prt1= prt;
	String[] pid1 = pid;
	int temp;
	String stemp;
	for (int i = 0; i < numberOfProcess; i++) 
	{

		for (int j = 0; j < numberOfProcess - i - 1; j++) 
		{
			if (at1[j] > at1[j + 1]) 
			{
				//swapping arrival time
				temp = at1[j];
				at1[j] = at1[j + 1];
				at1[j + 1] = temp;

				//swapping burst time
				temp = bt1[j];
				bt1[j] = bt1[j + 1];
				bt1[j + 1] = temp;

				//swapping priority
				temp = prt1[j];
				prt1[j] = prt1[j + 1];
				prt1[j+ 1] = temp;

				//swapping process identity
				stemp = pid[j];
				pid1[j] = pid1[j + 1];
				pid1[j + 1] = stemp;

			}
			//sorting according to priority when arrival timings are same
			if (at1[j] == at1[j + 1]) 
			{
				if (prt1[j] > prt1[j + 1]) 
				{
					//swapping arrival time
					temp = at1[j];
					at1[j] = at1[j + 1];
					at1[j + 1] = temp;

					//swapping burst time
					temp = bt1[j];
					bt1[j] = bt1[j + 1];
					bt1[j + 1] = temp;

					//swapping priority
					temp = prt1[j];
					prt1[j] = prt1[j + 1];
					prt1[j + 1] = temp;

					//swapping process identity
					stemp = pid1[j];
					pid1[j] = pid1[j + 1];
					pid1[j + 1] = stemp;

				}
			}
		}

	}
	//calculating waiting & turn-around time for each process
	finishTime[0] = at[0] + bt[0];
	turnAroundTime[0] = finishTime[0] - at[0];
	waitingTime[0] = turnAroundTime[0] - bt[0];

	for (int i = 1; i < numberOfProcess; i++) 
	{
		finishTime[i] = bt[i] + finishTime[i - 1];
		turnAroundTime[i] = finishTime[i] - at[i];
		waitingTime[i] = turnAroundTime[i] - bt[i];
	}
	float sum = 0;
	for (int n : waitingTime) 
	{
		sum += n;
	}
	float averageWaitingTime = sum / numberOfProcess;

	sum = 0;
	for (int n : turnAroundTime) 
	{
		sum += n;
	}
	float averageTurnAroundTime = sum / numberOfProcess;

	//print on console the order of processes along with their finish time & turn around time
	for(int kl=0;kl<numberOfProcess;kl++)
	{
		System.out.println(bt[kl]);
	}
	System.out.println("Priority Scheduling Algorithm : ");
	System.out.format("%20s%20s%20s%20s%20s%20s%20s\n", "ProcessId", "BurstTime", "ArrivalTime", "Priority", "FinishTime", "WaitingTime", "TurnAroundTime");
	for (int i = 0; i < numberOfProcess; i++) {
		System.out.format("%20s%20d%20d%20d%20d%20d%20d\n", pid[i], bt[i], at[i], prt[i], finishTime[i], waitingTime[i], turnAroundTime[i]);
	}

	System.out.format("%100s%20f%20f\n", "Average", averageWaitingTime, averageTurnAroundTime);
}
}

       
   
