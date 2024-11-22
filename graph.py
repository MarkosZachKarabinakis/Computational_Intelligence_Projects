import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.colors as mcolors

# Load dataset
data = pd.read_csv("dataset.csv")

# Define a color map for categories C1 to C9
category_colors = {
    "C1": "blue",
    "C2": "red",
    "C3": "green",
    "C4": "purple",
    "C5": "orange",
    "C6": "cyan",
    "C7": "magenta",
    "C8": "yellow",
    "C9": "brown"
}

# Create the plot
plt.figure(figsize=(8, 6))

# Loop over each category and plot its points with the corresponding color
for category, color in category_colors.items():
    # Filter the data for the current category
    subset = data[data['category'] == category]
    # Plot the points for this category
    plt.scatter(subset['x1'], subset['x2'], label=category, color=color, s=10, alpha=0.7)

# Add labels, title, and legend
plt.xlabel('x1')
plt.ylabel('x2')
plt.title('Dataset with Points Categorized (C1 to C9)')
plt.legend(title="Category")

# Display the plot
plt.tight_layout()
plt.show()
