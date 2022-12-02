package com.example.banco.databasesample;
import java.io.File;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.ListActivity;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;

import android.widget.ListView;

import android.widget.TextView;



public class AndroidExplorer extends ListActivity {



    private List<String> item = null,item2 = null;

    private List<String> path = null,path2 = null;

    private String root="/";

    private TextView myPath;



    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        myPath = (TextView)findViewById(R.id.path);

        getDir(root);

    }



    private void getDir(String dirPath)

    {

        myPath.setText("Location: " + dirPath);



        item = new ArrayList<String>();

        path = new ArrayList<String>();



        File f = new File(dirPath);

        File[] files = f.listFiles();



        if(!dirPath.equals(root))

        {



            item.add(root);

            path.add(root);



            item.add("../");

            path.add(f.getParent());



        }



      for(int i=0; i < files.length; i++)

      {

          File file = files[i];


          if (file.isDirectory()) {
              path.add(file.getPath());
              item.add(file.getName() + "/");
          }
      }
          String Supporto;
          Integer r, y, Supp;
          String[] Perordinare = item.toArray(new String[0]);
          boolean Nonfinito = true;
          while (Nonfinito) {
              y = 0;
              Nonfinito = false;
              while (y < Perordinare.length - 1) {
                  r = Perordinare[y].compareTo(Perordinare[y + 1]);
                  if (r > 0) {
                      Nonfinito = true;
                      Supporto = Perordinare[y];
                      Perordinare[y] = Perordinare[y + 1];
                      Perordinare[y + 1] = Supporto;
                      Supporto = item.get(y);
                      item.set(y, item.get(y + 1));
                      item.set(y + 1, Supporto);
                      Supporto = path.get(y);
                      path.set(y, path.get(y + 1));
                      path.set(y + 1, Supporto);

                  }
                  y = y + 1;
              }
          }




        item2 = new ArrayList<String>();

        path2 = new ArrayList<String>();
        for(int i=0; i < files.length; i++)

        {

            File file = files[i];

            if(!file.isDirectory())
            {
                path2.add(file.getPath());
                item2.add(file.getName());
            }
        }

       Perordinare=item2.toArray(new String[0]);
       Nonfinito=true;
        while (Nonfinito) {
            y=0;
            Nonfinito=false;
            while (y<Perordinare.length-1){
                r=Perordinare[y].compareTo(Perordinare[y+1]);
                if (r>0){
                    Nonfinito=true;
                    Supporto=Perordinare[y];
                    Perordinare[y]=Perordinare[y+1];
                    Perordinare[y+1]=Supporto;
                    Supporto=item2.get(y);
                    item2.set(y,item2.get(y+1));
                    item2.set(y+1,Supporto);
                    Supporto=path2.get(y);
                    path2.set(y,path2.get(y+1));
                    path2.set(y+1,Supporto);

                }
                y=y+1;
            }
        }

        for(int i=0; i < item2.size(); i++) {
            path.add(path2.get(i));
            item.add(item2.get(i));
        }


                ArrayAdapter<String> fileList =

                        new ArrayAdapter<String>(this, R.layout.row, item);

                setListAdapter(fileList);

    }



    @Override

    protected void onListItemClick(ListView l, View v, int position, long id) {



        File file = new File(path.get(position));



        if (file.isDirectory())

        {

            if(file.canRead())

                getDir(path.get(position));

            else

            {

                new AlertDialog.Builder(this)

                        .setIcon(R.drawable.icon)

                        .setTitle("[" +path.get(position) + "] folder can't be read!")

                        .setPositiveButton("OK",

                                new DialogInterface.OnClickListener() {



                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {

                                        // TODO Auto-generated method stub

                                    }

                                }).show();

            }

        }

        else

        {
            final Integer pos=position;
            new AlertDialog.Builder(this)

                    .setIcon(R.drawable.icon)

                    .setTitle("Ripristino database")
                    .setMessage("Importare\n\r[" +path.get(pos) + "]?")
                    .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {



                        @Override

                        public void onClick(DialogInterface dialog, int which) {

                            // TODO Auto-generated method stub

                        }

                    })
                    .setPositiveButton("OK",

                            new DialogInterface.OnClickListener() {



                                @Override

                                public void onClick(DialogInterface dialog, int which) {

                                    // TODO Auto-generated method stub
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("nomefile", path.get(pos));
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();

                                }

                            }).show();

        }

    }

}
