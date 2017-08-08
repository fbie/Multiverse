# Multiverse Software Transactional Memory #

A software transactional memory implementation for the JVM. Access (read and writes) to shared memory is done through transactional references, that can be compared to `AtomicReference` from `java.util.concurrent.*`.   Access to these references will be done under A (atomicity), C (consistency), I (isolation) semantics.

Unfortunately, the project's website is no longer maintained.  You can access the [Javadoc](http://itu.dk/people/fbie/multiverse-javadoc).


## Example ##

```java
import org.multiverse.api.references.*;
import static org.multiverse.api.StmUtils.*;

public class Account {
    private final TxnRef<Date> lastModified;
    private final TxnLong amount;

    public Account(long amount) {
       // NB: Cannot call TxnRef.set() from outside transactions.
       this.amount = newTxnLong(amount);
       this.lastModified = newTxnRef(new Date());
    }

    public Date getLastModifiedDate(){
        // NB: Cannot call TxnRef.get() from outside transactions.
        return lastModified.atomicWeakGet();
    }

    public long getAmount(){
         return amount.atomicWeakGet();
    }

    public static void transfer(final Account from, final Account to, final long amount){
        atomic(new Runnable()){
            public void run(){
                Date date = new Date();

                from.lastModified.set(date);
                from.amount.dec(amount);

                to.lastModified.set(date);
                to.amount.inc(amount);
            }
        }
    }

    public static main(String[] args) {
        Account account1 = new Account(10);
        Account account2 = new Account(20)
        Account.transfer(account1, account2, 5);
        System.out.println(account2.getAmount());
    }
}
```



## Compilation ##

Compile with Maven:

```bash
$ mvn compile
```



## No Instrumentation ##

Multiverse doesn't rely on instrumentation, so is easy to integrate in existing projects.
