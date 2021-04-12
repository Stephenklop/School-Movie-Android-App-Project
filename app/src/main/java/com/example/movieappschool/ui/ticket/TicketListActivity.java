package com.example.movieappschool.ui.ticket;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieappschool.R;
import com.example.movieappschool.data.LocalAppStorage;
import com.example.movieappschool.domain.Movie;
import com.example.movieappschool.domain.Seat;
import com.example.movieappschool.domain.Show;
import com.example.movieappschool.domain.Ticket;
import com.example.movieappschool.ui.menu.MenuActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TicketListActivity extends AppCompatActivity {

//    List<Ticket> ticketList = new ArrayList<Ticket>();
//    List<Movie> movieList = new ArrayList<Movie>();
//    List<Seat> seatList = new ArrayList<Seat>();
//    List<Show> showList = new ArrayList<Show>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    LocalAppStorage localAppStorage = new LocalAppStorage();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);

        // Menu
        View toolBar = findViewById(R.id.tickets_list_toolbar);
        ImageView hamburgerIcon = toolBar.findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("prevActivity", getClass().getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);

            getApplicationContext().startActivity(intent, options.toBundle());
        });

        fillTicketList();
        fillMovieList();
        fillSeatList();
        fillShowList();


        recyclerView = findViewById(R.id.tickets_list_items);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(this, /*this.getApplication()*/localAppStorage);
        recyclerView.setAdapter(mAdapter);
    }

    private void fillTicketList() {
        Ticket T1 = new Ticket(1, 1, 1, 1, 10.0);
        Ticket T2 = new Ticket(2, 1, 2, 1, 10.0);
        Ticket T3 = new Ticket(3, 1, 50, 1, 10.0);
        Ticket T4 = new Ticket(4, 2, 15, 1, 10.0);
        Ticket T5 = new Ticket(5, 2, 16, 1, 10.0);
        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.addAll(Arrays.asList(new Ticket[] { T1, T2, T3, T4, T5 }));
        localAppStorage.setTicketList(ticketList);
    }

    private void fillMovieList() {
        Movie M1 = new Movie(791373, "Zack Snyder's Justice League", "Gevoed door het herstelde vertrouwen in de mensheid en geïnspireerd door de onbaatzuchtige daad van Superman, roept Bruce Wayne de hulp in van zijn nieuwe bondgenoot, Diana Prince, om het hoofd te bieden aan een nog grotere vijand. Samen werken Batman en Wonder Woman snel om een team van meta-mensen te vinden en te rekruteren om deze nieuw ontwaakte dreiging te weerstaan. Maar ondanks de vorming van deze ongekende groep helden - Batman, Wonder Woman, Aquaman, Cyborg en The Flash - is het misschien al te laat om de planeet te redden van een aanval van catastrofale omvang.", "NL", null, "2021", "https://www.themoviedb.org/t/p/w220_and_h330_face/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg", 0.0, 0, 245);
        Movie M2 = new Movie(123456, "Test", "Gevoed door het herstelde vertrouwen in de mensheid en geïnspireerd door de onbaatzuchtige daad van Superman, roept Bruce Wayne de hulp in van zijn nieuwe bondgenoot, Diana Prince, om het hoofd te bieden aan een nog grotere vijand. Samen werken Batman en Wonder Woman snel om een team van meta-mensen te vinden en te rekruteren om deze nieuw ontwaakte dreiging te weerstaan. Maar ondanks de vorming van deze ongekende groep helden - Batman, Wonder Woman, Aquaman, Cyborg en The Flash - is het misschien al te laat om de planeet te redden van een aanval van catastrofale omvang.", "NL", null, "2021", "https://www.themoviedb.org/t/p/w220_and_h330_face/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg", 0.0, 0, 245);


        List<Movie> movieList = new ArrayList<Movie>();
        //movieList.add(M1);
        //movieList.add(M2);
        //localAppStorage.setMovies(movieList);
    }

    private void fillSeatList() {
        Seat S1 = new Seat(1, 1);
        Seat S2 = new Seat(2, 1);
        Seat S3 = new Seat(3, 1);
        Seat S4 = new Seat(4, 1);
        Seat S5 = new Seat(5, 1);
        Seat S6 = new Seat(6, 2);
        Seat S7 = new Seat(7, 2);
        Seat S8 = new Seat(8, 2);
        Seat S9 = new Seat(9, 2);
        Seat S10 = new Seat(10, 2);
        Seat S11 = new Seat(11, 2);
        Seat S12 = new Seat(12, 2);
        Seat S13 = new Seat(13, 2);
        Seat S14 = new Seat(14, 2);
        Seat S15 = new Seat(15, 3);
        Seat S16 = new Seat(16, 3);
        Seat S17 = new Seat(17, 3);
        Seat S18 = new Seat(18, 3);
        Seat S19 = new Seat(19, 3);
        Seat S20 = new Seat(20, 3);
        Seat S21 = new Seat(21, 3);
        Seat S22 = new Seat(22, 3);
        Seat S23 = new Seat(23, 3);
        Seat S24 = new Seat(24, 4);
        Seat S25 = new Seat(25, 4);
        Seat S26 = new Seat(26, 4);
        Seat S27 = new Seat(27, 4);
        Seat S28 = new Seat(28, 4);
        Seat S29 = new Seat(29, 4);
        Seat S30 = new Seat(30, 4);
        Seat S31 = new Seat(31, 4);
        Seat S32 = new Seat(32, 4);
        Seat S33 = new Seat(33, 5);
        Seat S34 = new Seat(34, 5);
        Seat S35 = new Seat(35, 5);
        Seat S36 = new Seat(36, 5);
        Seat S37 = new Seat(37, 5);
        Seat S38 = new Seat(38, 5);
        Seat S39 = new Seat(39, 5);
        Seat S40 = new Seat(40, 5);
        Seat S41 = new Seat(41, 5);
        Seat S42 = new Seat(42, 6);
        Seat S43 = new Seat(43, 6);
        Seat S44 = new Seat(44, 6);
        Seat S45 = new Seat(45, 6);
        Seat S46 = new Seat(46, 6);
        Seat S47 = new Seat(47, 6);
        Seat S48 = new Seat(48, 6);
        Seat S49 = new Seat(49, 6);
        Seat S50 = new Seat(50, 6);
        List<Seat> seatList = new ArrayList<Seat>();
        seatList.addAll(Arrays.asList(new Seat[] { S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S21, S22, S23, S24, S25, S26, S27, S28, S29, S30, S31, S32, S33, S34, S35, S36, S37, S38, S39, S40, S41, S42, S43, S44, S45, S46, S47, S48, S49, S50}));
        localAppStorage.setSeatList(seatList);
    }
    private void fillShowList() {
        Show SH1 = new Show(791373, "2021-04-10 15:10:00", 1, 3);
        List<Show> showList = new ArrayList<Show>();
        showList.add(SH1);
        localAppStorage.setShowList(showList);
    }
}
