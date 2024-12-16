# Overview
This project is an interactive system developed in JavaFX that demonstrates various interaction techniques:  
basic transforms, snap-to-grid, multiple select, object grouping, and undo/redo.  

The project is structured using a full MVC architecture and includes the following features:

# Features Implemented

## Part 1: Point Transforms and Snap-to-Grid
- **Line Creation**: Users can create new lines by pressing the **Shift** key and dragging the mouse.  
- **Endpoint Movement**: Users can move a line's endpoint by pressing and dragging the mouse.  
- **Snap-to-Grid**: Endpoints snap to a 20-pixel grid when the mouse is released.  
- **Line Movement**: Users can move existing lines by pressing and dragging.  
- **Line Selection**:  
  - Lines can be selected by clicking on them.  
  - Selected lines display their endpoints and are drawn in pink, while non-selected lines are drawn in dark purple.  
- **Hover Effect**: When the mouse is near a line, the line is shown with a 10-pixel-wide grey background.  
- **Deletion**: Selected lines can be deleted by pressing the **Delete** or **Backspace** keys.  
- **Rotation**: Selected lines can be rotated around their centers using:  
  - **Left-Arrow** (counter-clockwise)  
  - **Right-Arrow** (clockwise) keys.  
- **Scaling**: Selected lines can be scaled up or down around their centers using:  
  - **Up-Arrow** (scale up)  
  - **Down-Arrow** (scale down) keys.  

---

## Part 2: Multiple Select and Grouping
- **Multiple Selection**: Users can select multiple items by:  
  - Pressing **Control** and clicking on items.  
  - Dragging a rubber-band rectangle around the items.  
- **Rubber-Band Selection**:  
  - The rubber-band rectangle is shown as a dotted line.  
  - All items within the rectangle are selected when the mouse is released.  
- **Grouping**:  
  - Selected items can be grouped by pressing the **G** key.  
  - Groups are shown with a pink bounding box.  
- **Ungrouping**:  
  - Groups can be ungrouped by pressing the **U** key.  
  - Only the first selected group is ungrouped if multiple items are selected.  

---

## Part 3: Undo/Redo
- **Undo/Redo System**: Implemented using the command pattern. Commands include:  
  - Create line  
  - Delete items  
  - Adjust endpoint  
  - Move items  
  - Rotate items  
  - Scale items  
  - Group  
  - Ungroup  
- **Undo**: Pressing the **Z** key undoes the top command on the undo stack.  
- **Redo**: Pressing the **R** key redoes the top command on the redo stack.  

---

## Classes Implemented
- **EditorApp**: The main application class.  
- **MainUI**: Organizes the views into an interface.  
- **LineModel**: Stores all objects.  
- **DLine**: Represents line objects stored in the model.  
- **Endpoint**: Represents a line’s endpoint.  
- **DView**: An immediate-mode graphical view of the objects in the model.  
- **AppController**: Interprets all user events.  
- **InteractionModel**: Manages user interactions and selections.  
- **Rubberband**: Stores the details of the user’s current rubber-band rectangle.  
- **DGroup**: Holds a group of items.  
- **Groupable**: Interface for groupable objects.  
- **DCommand**: Interface for commands.  
  - **AdjustEPCommand**: Command for adjusting endpoints.  
  - **CreateLineCommand**: Command for creating lines.  
  - **DeleteItemCommand**: Command for deleting items.  
  - **GroupCommand**: Command for grouping items.  
  - **MoveCommand**: Command for moving items.  
  - **RotateCommand**: Command for rotating items.  
  - **ScaleCommand**: Command for scaling items.  
  - **UngroupCommand**: Command for ungrouping items.  
- **Subscriber**: Interface for subscribers to the model.  

---

## How to Run
1. Extract the folder.  
2. Open the project in IntelliJ and click **"Trust this project"**.  
3. Wait for the configuration to load.  
4. Run the **EditorApp** class.  

---

## Notes
The **DCommands** have been separated into a different folder for more clarity.
