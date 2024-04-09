import java.util.*;

class NutritionPlan {
    private int caloricIntake;
    private Map<String, Integer> macronutrientRatios;
    private List<String> mealPlans;
    private String fitnessGoal;
    private List<String> dietaryRestrictions;
    private List<String> dietaryPreferences;
    private List<String> mealTimings;

    public NutritionPlan() {
        this.macronutrientRatios = new HashMap<>();
        this.mealPlans = new ArrayList<>();
        this.dietaryRestrictions = new ArrayList<>();
        this.dietaryPreferences = new ArrayList<>();
        this.mealTimings = new ArrayList<>();
    }

    public int getCaloricIntake() {
        return caloricIntake;
    }

    public void setCaloricIntake(int caloricIntake) {
        this.caloricIntake = caloricIntake;
    }

    public Map<String, Integer> getMacronutrientRatios() {
        return macronutrientRatios;
    }

    public void setMacronutrientRatios(int carbohydrates, int proteins, int fats) {
        this.macronutrientRatios.put("carbohydrates", carbohydrates);
        this.macronutrientRatios.put("proteins", proteins);
        this.macronutrientRatios.put("fats", fats);
    }

    public List<String> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(List<String> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public List<String> getDietaryPreferences() {
        return dietaryPreferences;
    }

    public void setDietaryPreferences(List<String> dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }

    public List<String> getMealTimings() {
        return mealTimings;
    }

    public void setMealTimings(List<String> mealTimings) {
        this.mealTimings = mealTimings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Caloric Intake: ").append(caloricIntake).append(" kcal\n")
                .append("  Macronutrient Ratios:\n")
                .append("  \tCarbohydrates: ").append(macronutrientRatios.get("carbohydrates")).append("%\n")
                .append("  \tProteins: ").append(macronutrientRatios.get("proteins")).append("%\n")
                .append("  \tFats: ").append(macronutrientRatios.get("fats")).append("%\n")
                .append("  Meal Plans: ").append(mealPlans).append("\n")
                .append("  Fitness Goal: ").append(fitnessGoal).append("\n")
                .append("  Dietary Restrictions: ").append(dietaryRestrictions).append("\n")
                .append("  Dietary Preferences: ").append(dietaryPreferences).append("\n")
                .append("  Meal Timings: ").append(mealTimings).append("\n");

        return sb.toString();
    }
}

abstract class NutritionPlanBuilder {
    protected NutritionPlan nutritionPlan;

    public NutritionPlanBuilder() {
        nutritionPlan = new NutritionPlan();
    }

    public NutritionPlanBuilder setCaloricIntake(int caloricIntake) {
        nutritionPlan.setCaloricIntake(caloricIntake);
        return this;
    }

    public NutritionPlanBuilder setMacronutrientRatios(int carbohydrates, int proteins, int fats) {
        nutritionPlan.setMacronutrientRatios(carbohydrates, proteins, fats);
        return this;
    }

    public NutritionPlanBuilder setMealPlans(List<String> mealPlans) {
        nutritionPlan.setMealPlans(mealPlans);
        return this;
    }

    public NutritionPlanBuilder setFitnessGoal(String fitnessGoal) {
        nutritionPlan.setFitnessGoal(fitnessGoal);
        return this;
    }

    public NutritionPlanBuilder setDietaryRestrictions(List<String> dietaryRestrictions) {
        nutritionPlan.setDietaryRestrictions(dietaryRestrictions);
        return this;
    }

    public NutritionPlanBuilder setDietaryPreferences(List<String> dietaryPreferences) {
        nutritionPlan.setDietaryPreferences(dietaryPreferences);
        return this;
    }

    public NutritionPlanBuilder setMealTimings(List<String> mealTimings) {
        nutritionPlan.setMealTimings(mealTimings);
        return this;
    }

    public abstract NutritionPlan build();

    protected abstract List<String> generateRecommendedMealPlans();
}

class WeightLossNutritionPlanBuilder extends NutritionPlanBuilder {
    @Override
    public NutritionPlan build() {
        nutritionPlan.setMealPlans(generateRecommendedMealPlans());
        return nutritionPlan;
    }

    @Override
    protected List<String> generateRecommendedMealPlans() {
        List<String> recommendedMealPlans = new ArrayList<>();
        List<String> dietaryRestrictions = nutritionPlan.getDietaryRestrictions();

        for(String aa: dietaryRestrictions){
            recommendedMealPlans.add(aa + "-free meal");
        }
        if (recommendedMealPlans.isEmpty()) {
            recommendedMealPlans.add("Low-calorie meal plan");
            recommendedMealPlans.add("High-fiber meal plan");
        }
        return recommendedMealPlans;
    }
}

class NutritionPlanDirector {
    private NutritionPlanBuilder builder;

    public void setBuilder(NutritionPlanBuilder builder) {
        this.builder = builder;
    }

    public NutritionPlan createNutritionPlan() {
        return builder.build();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Nutrition Planner!");
        System.out.println("Let's start by creating your nutrition plan.");

        System.out.print("Enter your daily caloric intake (in kcal): ");
        int caloricIntake = scanner.nextInt();

        System.out.print("Enter the percentage of carbohydrates in your diet: ");
        int carbohydrates = scanner.nextInt();
        System.out.print("Enter the percentage of proteins in your diet: ");
        int proteins = scanner.nextInt();
        System.out.print("Enter the percentage of fats in your diet: ");
        int fats = scanner.nextInt();

        System.out.print("Enter your fitness goal (weight loss, weight gain, maintenance): ");
        scanner.nextLine();
        String fitnessGoal = scanner.nextLine();

        System.out.print("Enter any dietary restrictions (comma-separated list, e.g., gluten,lactose): ");
        String[] restrictionsArray = scanner.nextLine().split(",");
        List<String> dietaryRestrictions = Arrays.asList(restrictionsArray);

        System.out.print("Enter any dietary preferences (comma-separated list, e.g., vegetarian,organic): ");
        String[] preferencesArray = scanner.nextLine().split(",");
        List<String> dietaryPreferences = Arrays.asList(preferencesArray);

        System.out.print("Enter your meal timings (comma-separated list, e.g., 8:00 AM,1:00 PM,6:00 PM): ");
        String[] timingsArray = scanner.nextLine().split(",");
        List<String> mealTimings = Arrays.asList(timingsArray);

        NutritionPlanBuilder builder = new WeightLossNutritionPlanBuilder()
                .setCaloricIntake(caloricIntake)
                .setMacronutrientRatios(carbohydrates, proteins, fats)
                .setFitnessGoal(fitnessGoal)
                .setDietaryRestrictions(dietaryRestrictions)
                .setDietaryPreferences(dietaryPreferences)
                .setMealTimings(mealTimings);

        NutritionPlanDirector director = new NutritionPlanDirector();
        director.setBuilder(builder);
        NutritionPlan nutritionPlan = director.createNutritionPlan();
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║ Your nutrition plan has been created successfully: ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println(nutritionPlan);

        scanner.close();
    }
}
