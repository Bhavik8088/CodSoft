# 💱 Real-Time Currency Converter

A professional, meaningful Java Swing application that provides real-time currency conversion using live exchange rates. Built with a focus on clean code and a modern user interface.

![App Screenshot](screenshot_placeholder.png)

## 🚀 Features

- **Real-Time Data**: Fetches the latest exchange rates from [ExchangeRate-API](https://www.exchangerate-api.com/).
- **Modern UI**: A clean, responsive Swing interface with custom styling.
- **Error Handling**: Robust connection management and input validation.
- **Multithreading**: Prevents UI freezing by fetching data asynchronously.

## 🛠️ Tech Stack

- **Language**: Java
- **GUI Framework**: Swing (AWT)
- **Networking**: java.net.HttpURLConnection (No external dependencies!)
- **JSON Parsing**: Custom lightweight parser

## 📦 How to Run

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/currency-converter.git
    cd currency-converter
    ```

2.  **Compile the source**:
    ```bash
    javac CurrencyConverterApp.java ExchangeRateService.java
    ```

3.  **Run the application**:
    ```bash
    java CurrencyConverterApp
    ```

## 📸 Usage

1.  Enter the amount you wish to convert.
2.  Select the **Base Currency** (e.g., USD).
3.  Select the **Target Currency** (e.g., EUR).
4.  Click **Convert** to see the live result.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
