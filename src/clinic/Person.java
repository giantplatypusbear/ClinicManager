package InheritedAndRelated;

public class Person implements Comparable<Person>{
    protected Profile profile;

    public Person(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public int compareTo(Person other) {
        return this.profile.compareTo(other.profile);  // Use profile comparison
    }


    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return this.profile.equals(other.profile);
    }


    public String toString() {
        return profile.toString();
    }
}