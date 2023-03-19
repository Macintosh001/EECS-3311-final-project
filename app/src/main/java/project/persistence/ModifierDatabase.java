package project.persistence;
import project.objects.Modifier;

import java.util.List;

public interface ModifierDatabase {

        /**
         * Add modifier to db
         * @param mod a modifier
         */
        public void addModifier(Modifier mod);

        /**
         * remove a modifier from database
         * @param name of mod to be removed
         */
        public void removeModifier(String name);

        /**
         * get a modifier from database
         * @param name name of modifier retrieved
         * @return modifier with the given name, or null if no such mod exists
         */
        public Modifier getModifier(String name);

        /**
         * @return a complete set of modifiers currently in the database
        */
        public List<Modifier> getModifierList();

        /**
         * make the database empty
         */
        public void empty();


}
