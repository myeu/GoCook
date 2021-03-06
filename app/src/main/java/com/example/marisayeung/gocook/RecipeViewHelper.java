package com.example.marisayeung.gocook;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by marisayeung
 *
 * Contains common view building code for displaying recipe components
 *
 */

public class RecipeViewHelper {

    public static LinearLayout displayIngredient(Ingredient i, Context context) {
//        Add ingredient row
        LinearLayout ingredientLayout = new LinearLayout(context);
        ingredientLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams ingredientParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ingredientLayout.setLayoutParams(ingredientParams);
        //root.addView(ingredientLayout);

        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lineParams.setMargins(16, 0, 0, 0);

        TextView amountView = new TextView(context);
        amountView.setText(i.getAmount());
        amountView.setLayoutParams(lineParams);
        amountView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        amountView.setPadding(25, 8, 0, 8);
        ingredientLayout.addView(amountView);

//        TODO: check what fields are empty, see parser
        TextView unitView = new TextView(context);
        if (!i.getUnit().equals("each")) {
            unitView.setText(i.getUnit());
            unitView.setLayoutParams(lineParams);
            ingredientLayout.addView(unitView);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(i.getIngredient());
        if (!i.getProcessing().equals("")) {
            builder.append(", ");
            builder.append(i.getProcessing());
        }
        if (!i.getNote().equals("")) {
            builder.append(" Note: ");
            builder.append(i.getNote());
        }

        TextView ingredientView = new TextView(context);
        ingredientView.setText(builder.toString());
        ingredientView.setLayoutParams(lineParams);
        ingredientView.setPadding(25, 0, 0, 0);
        ingredientView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        ingredientLayout.addView(ingredientView);

        return ingredientLayout;
    }

    public static LinearLayout displayStep(String step, int num, Context context) {
//        Add Step row
        LinearLayout stepLayout = new LinearLayout(context);
        stepLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams stepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        stepLayout.setLayoutParams(stepParams);
        //root.addView(stepLayout);

//        Add number
        TextView number = new TextView(context);
        LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numberParams.setMargins(25, 0, 0, 0);
        number.setLayoutParams(numberParams);

        StringBuilder builder = new StringBuilder();
        builder.append(num);
        builder.append(". ");
        number.setText(builder);
        number.setPadding(25, 0, 0, 0);
        number.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        stepLayout.addView(number);

//        Add step
        TextView stepView = new TextView(context);
        LinearLayout.LayoutParams stepTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        stepTextParams.setMargins(16, 0, 25, 0);
        stepView.setLayoutParams(stepTextParams);

        stepView.setText(step);
        stepView.setLineSpacing(0, 1.5F);
        stepView.setPadding(0, 0, 25, 0);
        stepView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        stepLayout.addView(stepView);

        return stepLayout;
    }
}
