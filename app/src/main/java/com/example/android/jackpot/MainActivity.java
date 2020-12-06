package com.example.android.jackpot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int numberOfImagesInEachList=10;
    int numberOfForcedPairs=2;
    int durationBetweenFrames=90;

    ArrayList<IndexAndImage>  maleList= new ArrayList<>();
    ArrayList<IndexAndImage>  femaleList= new ArrayList<>();
    int [] forcedIndices = new int[numberOfForcedPairs];

    Random random;
    boolean animationStop=false;

    ImageView right;
    ImageView left;

    int[] maleIndices={0,1,2,3,4,5,6,7,8,9};
    int[] femaleIndices=maleIndices;

    int lastIndexMale=9;
    int lastIndexFemale=9;

    int n=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// this code hides navigation bar and makes the app in full screen mode//
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        // this array list helps the programmer or the client to enter the data of images easily
        // The client forced pairs should be of range from  index 0 : index numberOfForcedPairs-1
        maleList.add(new IndexAndImage(0,R.drawable.bannana));
        maleList.add(new IndexAndImage(1,R.drawable.apple));
        maleList.add(new IndexAndImage(2,R.drawable.orange));
        maleList.add(new IndexAndImage(3,R.drawable.lemon));
        maleList.add(new IndexAndImage(4,R.drawable.date));
        maleList.add(new IndexAndImage(5,R.drawable.pomengrate));
        maleList.add(new IndexAndImage(6,R.drawable.berry));
        maleList.add(new IndexAndImage(7,R.drawable.download));
        maleList.add(new IndexAndImage(8,R.drawable.watermelon));
        maleList.add(new IndexAndImage(9,R.drawable.guova));

        femaleList.add(new IndexAndImage(0,R.drawable.yellowpepper));
        femaleList.add(new IndexAndImage(1,R.drawable.tommato));
        femaleList.add(new IndexAndImage(2,R.drawable.potato));
        femaleList.add(new IndexAndImage(3,R.drawable.pea));
        femaleList.add(new IndexAndImage(4,R.drawable.onion));
        femaleList.add(new IndexAndImage(5,R.drawable.garlic));
        femaleList.add(new IndexAndImage(6,R.drawable.eggplant));
        femaleList.add(new IndexAndImage(7,R.drawable.lattice));
        femaleList.add(new IndexAndImage(8,R.drawable.cucumber));
        femaleList.add(new IndexAndImage(9,R.drawable.carrot));

        final AnimationDrawable animationDrawable = new AnimationDrawable();
        final AnimationDrawable animationDrawable1 = new AnimationDrawable();


        right =(ImageView) findViewById(R.id.right);
        left =(ImageView) findViewById(R.id.left);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.linearlayout);

        //Sets onClickListener to pause and play tha animation//

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animationStop==false){
                    animationDrawable.stop();
                    animationDrawable1.stop();

                    animationStop=true;

                }
                else{
                    animationDrawable.start();
                    animationDrawable1.start();

                    animationStop=false;

                }

            }
        });
        // Randomly chooses indices for the chosen two images that must show up at the same time, this should not be left for luck to decide //
        random = new Random();
        int i=0;
        int r=-1;

                while(i<numberOfForcedPairs){
                    if(i==0){

                    r = random.nextInt((numberOfImagesInEachList/numberOfForcedPairs))+1;}
                    else{r += random.nextInt((numberOfImagesInEachList/numberOfForcedPairs))+1;
                    }

                    if(contains(forcedIndices,r)==false){
                        forcedIndices[i]=r;
                        i++;
                }

        }
                int counterForWhile=0;
                int counterForForccedIndex=0;

                // adds number of frames as desired and makes sure the max number of images repeated is 1 for regular images and 2 for images //
                while (counterForWhile<numberOfImagesInEachList){


                    if(counterForForccedIndex<numberOfForcedPairs && forcedIndices[counterForForccedIndex]==counterForWhile){

                        animationDrawable.addFrame(getResources().getDrawable(femaleList.get(counterForForccedIndex).getImageId()),durationBetweenFrames);
                        animationDrawable1.addFrame(getResources().getDrawable(maleList.get(counterForForccedIndex).getImageId()),durationBetweenFrames);

                        counterForWhile++;
                        counterForForccedIndex++;

                        continue;

                    }

                    int rMale= random.nextInt(lastIndexMale);
                    int rFemale= random.nextInt(lastIndexFemale);


                    if(rMale!=rFemale || counterForWhile==numberOfImagesInEachList-1){

                        int female=femaleIndices[rFemale];
                        int male=maleIndices[rMale];

                        animationDrawable.addFrame(getResources().getDrawable(femaleList.get(female).getImageId()),durationBetweenFrames);
                        animationDrawable1.addFrame(getResources().getDrawable(maleList.get(male).getImageId()),durationBetweenFrames);

                        maleIndices= remove(maleIndices,rMale);
                        femaleIndices=remove(femaleIndices,rFemale);

                        lastIndexMale--;
                        lastIndexFemale--;

                        counterForWhile++;

                    }

                 n++;
                }
                right.setBackground(animationDrawable);
                left.setBackground(animationDrawable1);
                Log.v("repeat",String.valueOf(n));

                animationDrawable.start();
                animationDrawable1.start();

    }
    // removes the integer from an array by just knowing the index//
    static int[] remove(int[] arr ,int index){
        int[] finalArray=new int[(arr.length)-1];

        boolean numberWantedDone =false;

        for(int i=0;i<arr.length-1;i++){

            if(i==index || numberWantedDone) {

                finalArray[i]=arr[i+1];
                numberWantedDone=true;

            }
             else{
                finalArray[i]=arr[i];
             }

        }
        return finalArray;
    }
    // does the array contain the number or not?
    public static boolean contains(int[]arr, int number)
    {
        int h;
        boolean containsNumber = false;
        for (h = 0; h < arr.length; h++)
        {
            if (arr[h] == number)
            {
                containsNumber = true;
                break;
            }

        }

        return containsNumber;
    }
// adds a number to an array and make it have the last index of the array//
    static int[] add(int[] arr ,int number){
        int length=arr.length;
        int[] finalArray=new int[length+1];
        int i;
        int g=0;
        for(i=0;i<arr.length+1;i++){
            if(i==length){
                finalArray[i]=number;
                continue;
            }
            finalArray[i]=arr[i];



        }
        return finalArray;
    }
}
