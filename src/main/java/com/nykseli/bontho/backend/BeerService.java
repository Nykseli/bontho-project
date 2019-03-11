package com.nykseli.bontho.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 *
 * {@link BeerService#getInstance()}.
 */
public class BeerService {

    private static BeerService instance;
    private static final Logger LOGGER = Logger.getLogger(BeerService.class.getName());

    private final HashMap<Long, Beer> beers = new HashMap<>();
    private long nextId = 0;

    private BeerService() {
    }

    /**
     * @return a reference to an example facade for Beer objects.
     */
    public static BeerService getInstance() {
        if (instance == null) {
            instance = new BeerService();
            instance.ensureTestData();
        }
        return instance;
    }

    /**
     * @return all available Beer objects.
     */
    public synchronized List<Beer> findAll() {
        return findAll(null);
    }

    /**
     * Finds all Beer's that match given filter.
     *
     * @param stringFilter filter that returned objects should match or null/empty
     *                     string if all objects should be returned.
     * @return list a Beer objects
     */
    public synchronized List<Beer> findAll(String stringFilter) {
        ArrayList<Beer> arrayList = new ArrayList<>();
        for (Beer contact : beers.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(BeerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Beer>() {

            @Override
            public int compare(Beer o1, Beer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    /**
     * Finds all Beer's that match given filter and limits the resultset.
     *
     * @param stringFilter filter that returned objects should match or null/empty
     *                     string if all objects should be returned.
     * @param start        the index of first result
     * @param maxresults   maximum result count
     * @return list a Beer objects
     */
    public synchronized List<Beer> findAll(String stringFilter, int start, int maxresults) {
        ArrayList<Beer> arrayList = new ArrayList<>();
        for (Beer contact : beers.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(BeerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Beer>() {

            @Override
            public int compare(Beer o1, Beer o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        int end = start + maxresults;
        if (end > arrayList.size()) {
            end = arrayList.size();
        }
        return arrayList.subList(start, end);
    }

    /**
     * @return the amount of all Beers in the system
     */
    public synchronized long count() {
        return beers.size();
    }

    /**
     * Deletes a Beer from a system
     *
     * @param value the Beer to be deleted
     */
    public synchronized void delete(Beer value) {
        beers.remove(value.getId());
    }

    /**
     * Persists or updates Beer in the system. Also assigns an identifier for new
     * Beer instances.
     *
     * @param entry
     */
    public synchronized void save(Beer entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Beer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
            return;
        }
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Beer) entry.clone();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        beers.put(entry.getId(), entry);
    }

    /**
     * Sample data generation
     */
    public void ensureTestData() {
        if (findAll().isEmpty()) {
            final String[] names = new String[] { "Karhu III", "Pirkka III", "Sinebrychoff Koff" };
            Random r = new Random(0);
            for (String name : names) {
                String[] split = name.split(" ");
                Beer c = new Beer();
                c.setBrand(split[0]);
                c.setName(split[1]);
                c.setStatus(BeerStatus.values()[r.nextInt(BeerStatus.values().length)]);
                c.setCreated(LocalDate.now());
                save(c);
            }
        }
    }
}
