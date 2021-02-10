package util_tree;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;


public class DecisionTree implements Serializable, Cloneable {
    private int id;
    private static int nbTempNode = 1;
    private static int nbTempLeave = 1;
    private Node value;
    private DecisionTree treeLeft;
    private DecisionTree treeRight;

    // Constructeur avec seulement une valeur (sans fils)
    public DecisionTree(Node value) {
        this.value = value;
        this.treeLeft = null;
        this.treeRight = null;
    }

    // Constructeur avec une valeur, un fils droit et un fils gauche
    public DecisionTree(Node value, DecisionTree treeLeft, DecisionTree treeRight) {
        this.value = value;
        this.treeLeft = treeLeft;
        this.treeRight = treeRight;
    }

    //Constructeur qui génère un ProgrammeGénétique avec un certains nombre de noeuds (utilisé pour les tests)
    public DecisionTree(int nbNodes) throws IOException {
        Random r = new Random();
        Node allActions[] = getActTab();
        Node allConditions[] = getCondTab();

        //On crée un arbre de base avec une condition et deux actions
        int randCond = (int) (Math.random() * allConditions.length);
        int randAct1 = (int) (Math.random() * allActions.length);
        int randAct2 = (int) (Math.random() * allActions.length);
        this.value = allConditions[randCond];
        this.treeLeft = new DecisionTree(allActions[randAct1]);
        this.treeRight = new DecisionTree(allActions[randAct2]);

        //Boucle sur le nombre de noeud pour ajouter une condition (aléatoire) à chaque itération
        for (int i = 0; i < nbNodes - 1; i++) {
            add(allActions, allConditions);
        }
        simplifyTree();
        simplifyTree();
        numberingLeave();
        numberingNode();
    }

    //Méthode permmettant d'ajouter une condition à un programme (utilise la méthode récursive ci-dessous)
    public void add(Node[] allActions, Node[] allConditions) {
        Random r = new Random();
        int lr = (int) (Math.random() * 2); // 0 or 1
        if (lr == 0) this.treeLeft = addRecursive(this.treeLeft, allActions, allConditions);
        else this.treeRight = addRecursive(this.treeRight, allActions, allConditions);
    }

    //Méthode récursive permettant de placer une condition aléatoirement en fin d'arbre
    private DecisionTree addRecursive(DecisionTree current, Node[] allActions, Node[] allConditions) {
        if (current == null) {
            int randAct = (int) (Math.random() * allActions.length);
            current = new DecisionTree(allActions[randAct]);
            return current;
        }
        Random r = new Random();
        //Une chance sur deux d'aller à droite (resp. à gauche)
        int lr = (int) (Math.random() * 2); // 0 or 1

        //Si on était sur une action, on crée une condition à la place avec deux actions comme suites.
        if (current.value instanceof Action) {
            int randCond = (int) (Math.random() * allConditions.length);
            current.value = allConditions[randCond];
            int randAct1 = (int) (Math.random() * allActions.length);
            int randAct2 = (int) (Math.random() * allActions.length);

            current.treeLeft = new DecisionTree(allActions[randAct1]);
            current.treeRight = new DecisionTree(allActions[randAct2]);
            return current;
        }
        //On continue si on est toujours pas sur une feuille (i.e. une action)
        if (lr == 0) {
            current.treeLeft = addRecursive(current.treeLeft, allActions, allConditions);
        } else if (lr == 1) {
            current.treeRight = addRecursive(current.treeRight, allActions, allConditions);
        }
        return current;
    }


    public DecisionTree() throws IOException {
        this(new Random().nextInt(100));
    }

    public Node getNoeud() {
        return value;
    }

    public String getValeurNoeud() {
        return value.getText();
    }

    public DecisionTree getTreeLeft() {
        return treeLeft;
    }

    public DecisionTree getTreeRight() {
        return treeRight;
    }

    public int getId() {
        return id;
    }

    // Fonction interne permettant de récupérer le texte présent dans les fichiers de Noeuds (Conditions et Actions)
    private List<String> getLines(String fileName) throws IOException { // Réutilisation de getLignes, présent dans la classe Case, faut-il le généraliser dans un classe mère ?
        List<String> lignes = new ArrayList<>();
        fileName = System.getProperty("user.dir") + "\\Module_Projet_Java\\" + fileName;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            lignes.add(line);
        }
        reader.close();
        return lignes;
    }

    // Fonction permettant d'afficher l'arbre en console
    public void displayTree() {
        displayTreePrivate(0, "");
    }

    private void displayTreePrivate(int hauteur, String espace) {
        System.out.println(espace + hauteur + "." + value.getText() + "(" + id + ")");
        if (value.getClass().getName().equals("util_fourmi.Condition")) {
            treeLeft.displayTreePrivate(hauteur + 1, espace += "  ");
            treeRight.displayTreePrivate(hauteur + 1, espace);
        }
    }



    public void simplifyTree() {
        //On simplifie les deux côtés de l'arbre de départ
        Map<String, String> m = new HashMap<>();
        m.put(this.value.getText(), "Left");
        this.treeLeft = simplifyTreeRecursive(this.treeLeft, m);
        //this.aGauche= simplifierProgrammeRecursif(this.aGauche,m);
        m.replace(this.value.getText(), "Right");
        this.treeRight = simplifyTreeRecursive(this.treeRight, m);
        //this.aDroite= simplifierProgrammeRecursif(this.aDroite,m);
    }

    private DecisionTree simplifyTreeRecursive(DecisionTree current, Map<String, String> map) {
        if (current == null) return null;
        //On recherche si on est déjà passé par
        String value = map.get(current.value.getText());

        //Si on est déjà passé par la condition on simplifie directement en prenant le côté correspondant
        if (value != null) {
            if (value.equals("Right")) {
                return simplifyTreeRecursive(current.treeRight, map);
            } else {
                return simplifyTreeRecursive(current.treeLeft, map);
            }
        }
        //Si on est sur une condition et que les deux côtés mènent à la même action, on simplifie en une Action.
        if (current.value instanceof Condition && current.treeLeft.value instanceof Action && current.treeRight.value instanceof Action && current.treeLeft.value.getText().equals(current.treeRight.value.getText()))
            return current.treeLeft;

        //Si on est sur une condition on continue la simplification sur les conditions suivantes
        if (current.treeLeft != null && !(current.treeLeft.value instanceof Action)) {
            //On ajoute le passage par le noeud actuel à la map .
            //NOTE : on a pas besoin de vérifier si la valeur était déjà présente dans la carte puisque si c'était le cas on aurait déjà simplifié dans la condition value!=null
            map.put(current.value.getText(), "Right");
            current.treeLeft = simplifyTreeRecursive(current.treeLeft, map);
            //Une fois qu'on a simplifié à gauche on enleve la valeur de la map
            map.remove(current.value.getText());
        }
        if (current.treeRight != null && !(current.treeRight.value instanceof Action)) {
            //Meme chose qu'a gauche
            map.put(current.value.getText(), "Right");
            current.treeRight = simplifyTreeRecursive(current.treeRight, map);
            map.remove(current.value.getText());
        }
        return current;
    }

    // Fonction permettant de numéroter les noeuds (ici les conditions) de 1 à nbNoeuds afin de les retrouver par la suite
    public void numberingNode() {
        numberingNodePrivate();
        nbTempNode = 1;
    }

    private void numberingNodePrivate() {
        if (value.getClass().getName().equals("util_fourmi.Condition")) {
            id = nbTempNode;
            nbTempNode++;
            treeLeft.numberingNodePrivate();
            treeRight.numberingNodePrivate();
        }
    }

    // Fonction permettant de numéroter les feuilles (ici les actions) de 1 à nbFeuilles afin de les retrouver par la suite
    public void numberingLeave() {
        numberingLeavePrivate();
        nbTempLeave = 1;
    }

    private void numberingLeavePrivate() {
        if (value.getClass().getName().equals("util_fourmi.Condition")) {
            treeLeft.numberingLeavePrivate();
            treeRight.numberingLeavePrivate();
        } else {
            id = nbTempLeave + 100; // On ajoute temporairement 100 pour différencier les noeuds des feuilles ensuite
            nbTempLeave++;
        }
    }

    // Fonction permettant de remplacer une condition par une autre afin de muter
    public void replaceCondition() throws IOException {
        int nbCond = nbConditions();
        //System.out.println("\nNombre de conditions : " + nbCond);
        if (nbCond != 0) {
            int randCond = (int) (Math.random() * nbCond) + 1;
            //System.out.println("N° du noeud à modifier : " + aleatCond);
            replaceConditionPrivate(randCond);
        }
        simplifyTree();
        numberingNode();
        numberingLeave();
    }

    private void replaceConditionPrivate(int randCond) throws IOException {
        if (id == randCond) {
            //System.out.println("Condition à modifier : " + valeur.getText());
            Node allConditions[] = getCondTab();

            int newNuCond = (int) (Math.random() * 3);
            while (allConditions[newNuCond].getText().equals(value.getText())) {
                newNuCond = (int) (Math.random() * 3);
            }
            value = allConditions[newNuCond];
        } else {
            if (value.getClass().getName().equals("util_fourmi.Condition")) {
                treeLeft.replaceConditionPrivate(randCond);
                treeRight.replaceConditionPrivate(randCond);
            }
        }
    }

    // Fonction permettant de remplacer une action par une autre afin de muter
    public void remplacerAction() throws IOException {
        int nbAct = nbActions();
        int aleatAct = (int) (Math.random() * nbAct) + 101; // A modifier ensuite selon nos choix de numérotation
        interneRemplacerAction(aleatAct);
        simplifyTree();
        numberingNode();
        numberingLeave();
    }

    private void interneRemplacerAction(int randAct) throws IOException {
        if (id == randAct) {
            Node allActions[] = getActTab();

            int newNuAct = (int) (Math.random() * 8);
            while (allActions[newNuAct].getText().equals(value.getText())) {
                newNuAct = (int) (Math.random() * 8);
            }
            value = allActions[newNuAct];
        } else {
            if (value.getClass().getName().equals("util_fourmi.Condition")) {
                treeLeft.interneRemplacerAction(randAct);
                treeRight.interneRemplacerAction(randAct);
            }
        }
    }

    // Fonction permettant d'échanger deux sous arbres d'une condition afin de muter
    public void exchangeChilds() throws IOException {
        int nbCond = nbConditions();
        if (nbCond != 0) {
            int randCond = (int) (Math.random() * nbCond) + 1;
            exchangeChildsPrivate(randCond);
        }
        simplifyTree();
        numberingNode();
        numberingLeave();
    }

    private void exchangeChildsPrivate(int randCond) throws IOException {
        if (id == randCond) {
            DecisionTree temp = treeLeft;
            treeLeft = treeRight;
            treeRight = temp;
        } else {
            if (value.getClass().getName().equals("util_fourmi.Condition")) {
                treeLeft.exchangeChildsPrivate(randCond);
                treeRight.exchangeChildsPrivate(randCond);
            }
        }
    }

    // Fonction permettant de remplacer un sous arbre d'un programme génétique par un sous arbre d'un autre programme génétique
    // Le programme génétique sur lequel on appelle la fonction sera celui sur lequel on remplace un sous-arbre
    // Le programme génétique passé en paramètre sera celui sur lequel on récupère le sous arbre
    public void replaceChild(DecisionTree tree2) throws IOException {
        int nbCond1 = nbConditions();
        int nbCond2 = tree2.nbConditions();
        if (nbCond1 != 0 && nbCond2 != 0) { // Ce cas ne devrait jamais arriver ensuite puisque la fonction sera appliquée sur les meilleurs arbres génétiques
            int randCond1 = (int) (Math.random() * nbCond1) + 1;
            int randCond2 = (int) (Math.random() * nbCond2) + 1;
            selectNodeToInsert(randCond1, randCond2, tree2);
        }
        simplifyTree();
        numberingNode();
        numberingLeave();
    }

    private void selectNodeToInsert(int randCond1, int randCond2, DecisionTree prog2) throws IOException {
        if (prog2.getId() == randCond2) {
            replaceChildPrivate(randCond1, randCond2, prog2);
        } else {
            if (prog2.getNoeud().getClass().getName().equals("util_fourmi.Condition")) {
                selectNodeToInsert(randCond1, randCond2, prog2.getTreeLeft());
                selectNodeToInsert(randCond1, randCond2, prog2.getTreeRight());
            }
        }
    }

    private void replaceChildPrivate(int randCond1, int randCond2, DecisionTree prog2) throws IOException {
        if (id == randCond1) {
            value = prog2.getNoeud();
            treeLeft = prog2.getTreeLeft();
            treeRight = prog2.getTreeRight();
        } else {
            if (value.getClass().getName().equals("util_fourmi.Condition")) {
                treeLeft.replaceChildPrivate(randCond1, randCond2, prog2);
                treeRight.replaceChildPrivate(randCond1, randCond2, prog2);
            }
        }
    }

    // Fonction permettant de cloner l'arbre et d'en créer un nouveau ayant les mêmes valeurs
    public DecisionTree clone() throws CloneNotSupportedException {
        DecisionTree p = (DecisionTree) super.clone();
        if (treeLeft != null) {
            p.treeLeft = (DecisionTree) this.treeLeft.clone();
            p.treeRight = (DecisionTree) this.treeRight.clone();
        }
        p.value = (Node) this.value.clone();
        return p;
    }

    // Fonction qui retourne la hauteur de l'arbre
    public int getTreeDepth() {
        if (this.getTreeRight() == null && this.getTreeLeft() == null)
            return 0;
        else
            return (1 + Math.max(getTreeLeft().getTreeDepth(), getTreeRight().getTreeDepth()));
    }

    // Fonction qui retourne le nombre de conditions présentes dans l'arbre
    public int nbConditions() {
        if (value.getClass().getName().equals("util_fourmi.Condition"))
            return 1 + treeLeft.nbConditions() + treeRight.nbConditions();
        else
            return 0;
    }

    // Fonction qui retourne le nombre d'actions présentes dans l'arbre
    public int nbActions() {
        return nbNodeTotal() - nbConditions();
    }

    // Fonction qui retourne le nombre de noeuds (Actions + Conditions) présents dans l'arbre
    public int nbNodeTotal() {
        if (value.getClass().getName().equals("util_fourmi.Condition")) {
            return 1 + treeLeft.nbNodeTotal() + treeRight.nbNodeTotal();
        } else {
            return 1;
        }
    }

    private Node[] getCondTab() throws IOException {
        String nomFichierConditions = "Noeuds\\Conditions.txt";

        // On récupère les conditions puis on les met dans une liste
        List<String> listConditions = getLines(nomFichierConditions);

        // La liste est transformée en tableau contenant des conditions
        Node allConditions[] = new Condition[listConditions.size()];

        for (int i = 0; i < listConditions.size(); i++) {
            allConditions[i] = new Condition(listConditions.get(i));
        }

        return allConditions;
    }

    private Node[] getActTab() throws IOException {
        String nomFichierActions = "Noeuds\\Actions.txt";

        // On récupère les actions puis on les met dans une liste
        List<String> listActions = getLines(nomFichierActions);

        // La liste est transformée en tableau contenant des actions
        Node allActions[] = new Action[listActions.size()];

        for (int i = 0; i < listActions.size(); i++) {
            allActions[i] = new Action(listActions.get(i));
        }

        return allActions;
    }

    // Fonction toString du programme génétique
    public String toString() {
        return toStringPrivate(0, "");
    }

    private String toStringPrivate(int hauteur, String espace) {
        String S = "";
        S += espace + hauteur + "." + value.getText() + "\n";
        if (value.getClass().getName().equals("util_fourmi.Condition")) {
            S += treeLeft.toStringPrivate(hauteur + 1, espace += "  ");
            S += treeRight.toStringPrivate(hauteur + 1, espace);
        }
        return S;
    }
}