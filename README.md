# SmartCart - Android Shopping List Application

A modern, feature-rich Android shopping list application built with Jetpack Compose and Material Design 3.

## 📱 Features

### Core Functionality
- **📝 Shopping List Management** - Create, edit, delete, and organize shopping lists
- **🛒 Item Management** - Add, edit, delete, and check off items with quantities and categories
- **📊 Progress Tracking** - Visual progress indicators showing completion status
- **🔄 Item Reordering** - Reorder items within lists using up/down arrows
- **🎨 Dark/Light Theme** - Toggle between themes with a dedicated settings button

### Advanced Features
- **📂 Dynamic Categories** - 10 predefined categories with custom colors plus ability to add custom ones
- **✏️ Inline Editing** - Edit both list names and item details with intuitive dialogs
- **💾 Persistent Storage** - All data saved locally using Room database
- **🎯 Smart UI** - Material 3 design with responsive layouts and smooth animations

## 🛠️ Technical Architecture

### Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Persistence Library
- **Reactive Programming**: Kotlin Flow & Coroutines
- **Design System**: Material Design 3
- **Build System**: Gradle with KSP

### Project Structure
```
app/src/main/java/com/smartcart/
├── MainActivity.kt                 # Main entry point with navigation
├── ListDetailScreen.kt            # Item management screen
├── data/
│   ├── entity/
│   │   ├── ShoppingListEntity.kt   # List database model
│   │   ├── ShoppingItemEntity.kt   # Item database model
│   │   └── CategoryEntity.kt       # Category database model
│   ├── dao/
│   │   ├── ShoppingListDao.kt      # List database operations
│   │   ├── ShoppingItemDao.kt      # Item database operations
│   │   └── CategoryDao.kt          # Category database operations
│   ├── database/
│   │   ├── SmartCartDatabase.kt    # Room database configuration
│   │   ├── Converters.kt           # Type converters for Date
│   │   └── DatabaseSeeder.kt       # Sample data initialization
│   └── repository/
│       └── SmartCartRepository.kt  # Data abstraction layer
├── viewmodel/
│   └── SmartCartViewModel.kt       # UI state management
└── ui/theme/                       # Material 3 theming
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

### Database Schema
- **shopping_lists**: List entities with completion status and timestamps
- **shopping_items**: Item entities with categories, quantities, and sort order
- **categories**: Custom categories with names and color codes

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog 2023.1.1 or later
- JDK 8 or higher
- Android SDK API 24+ (Android 7.0)
- Kotlin 1.9.0+

### Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/smartcart.git
   cd smartcart
   ```

2. **Open in Android Studio:**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory and select it

3. **Sync Project:**
   - Android Studio will automatically detect it's a Gradle project
   - Click "Sync Now" when prompted
   - Wait for dependencies to download

4. **Run the App:**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green triangle) or press Shift+F10
   - Select your target device

### Sample Data
The app automatically seeds the database with sample data on first run:
- **3 Shopping Lists**: Groceries, Electronics, Home Supplies
- **15+ Sample Items**: Various items across different categories
- **10 Default Categories**: With Material Design colors

## 🎨 User Interface

### Material Design 3
- **Dynamic theming** with light and dark modes
- **Adaptive colors** that respond to system settings
- **Consistent spacing** and typography throughout
- **Smooth animations** and transitions

### Screen Flow
1. **Main Screen**: List of shopping lists with completion status
2. **List Detail Screen**: Items within a selected list with management options
3. **Add/Edit Dialogs**: Modal dialogs for creating and editing content

### Accessibility
- Content descriptions for all interactive elements
- Material 3 accessibility standards compliance
- High contrast support in dark mode
- Touch target sizing follows Android guidelines

## 🔧 Key Components

### Data Layer
- **Room Database** with proper entity relationships and foreign keys
- **Repository Pattern** for clean data abstraction
- **Type Converters** for complex data types (Date objects)
- **Database Migrations** supporting schema evolution

### Business Logic
- **ViewModels** managing UI state with lifecycle awareness
- **Coroutines** for asynchronous database operations
- **Flow** for reactive data streams and UI updates
- **Validation** for user input and edge cases

### UI Layer
- **Jetpack Compose** for modern declarative UI
- **State Management** with remember and collectAsState
- **Navigation** with simple screen state management
- **Reusable Components** for consistent design

## 📋 Feature Details

### Shopping Lists
- Create new lists with custom names
- Edit existing list names inline
- Delete lists (cascades to remove all items)
- Visual progress indicators showing completion percentage
- Timestamp tracking for creation and updates

### Shopping Items
- Add items with name, quantity, and category selection
- Edit all item properties after creation
- Mark items as completed/incomplete with visual feedback
- Reorder items within lists using up/down arrows
- Delete individual items
- Automatic sort order management

### Categories
- 10 predefined categories with Material Design colors
- Support for custom categories (extensible for future versions)
- Color-coded category indicators
- Dropdown selection in item dialogs

### Theme System
- System-aware light/dark theme detection
- Manual theme toggle via settings button
- Consistent color application across all components
- Material 3 dynamic color support

## 🧪 Testing

### Manual Testing Checklist
- [ ] Create and delete shopping lists
- [ ] Add, edit, and delete items
- [ ] Toggle item completion status
- [ ] Reorder items using arrow buttons
- [ ] Switch between light and dark themes
- [ ] Test category selection in dialogs
- [ ] Verify data persistence across app restarts
- [ ] Test empty states and edge cases

### Performance Considerations
- Efficient database queries with proper indexing
- Lazy loading for large lists
- Optimized Compose recomposition
- Memory-efficient image and data handling

## 🔮 Future Enhancements

### Potential Features
- **Cloud Sync** with user accounts
- **List Sharing** between multiple users
- **Barcode Scanning** for easy item addition
- **Price Tracking** and budget management
- **Voice Input** for hands-free item addition
- **Smart Suggestions** based on purchase history
- **Export/Import** functionality
- **Widget Support** for home screen access

### Technical Improvements
- **Unit Tests** for business logic
- **UI Tests** for user interactions
- **Performance Monitoring** with benchmarks
- **Crashlytics** integration for error tracking
- **Analytics** for usage insights

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Make your changes following the existing code style
4. Test thoroughly on different devices and orientations
5. Submit a pull request with detailed description

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex business logic
- Maintain consistent indentation and formatting
- Follow Material Design guidelines for UI components

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Development Team** - Initial work and implementation
- **UI/UX Design** - Material Design 3 implementation
- **Architecture** - MVVM and Clean Architecture patterns

## 🙏 Acknowledgments

- **Material Design Team** for comprehensive design guidelines
- **Android Jetpack Team** for modern development tools
- **Kotlin Team** for the excellent programming language
- **Open Source Community** for inspiration and best practices

## 📞 Support

For support, bug reports, or feature requests:
- Create an issue in the GitHub repository
- Follow the issue template for detailed information
- Check existing issues before creating new ones

---

**SmartCart** - Making shopping lists smart, simple, and beautiful! 🛒✨