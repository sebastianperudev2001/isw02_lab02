/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laboratorio_02;

/**
 *
 * @author jacks
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InvestmentPortfolio {
    
    private String userId;
    private List<Transaction> transactions;
    private Map<String, Double> fundBalances;
    
    public InvestmentPortfolio(String userId) {
        this.userId = userId;
        this.transactions = new ArrayList<>();
        this.fundBalances = new HashMap<>();
    }
    
    public void processBuyTransaction(String fundCode, double amount) {
        // Validaciones para transacción BUY
        if (fundCode == null || fundCode.isEmpty()) {
            throw new IllegalArgumentException("El código del fondo no puede estar vacío");
        }
        
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        
        // Verificar si el fondo existe
        if (!isFundValid(fundCode)) {
            throw new IllegalArgumentException("El fondo no existe: " + fundCode);
        }
        
        // Crear la transacción BUY
        String transactionId = UUID.randomUUID().toString();
        Date transactionDate = new Date();
        Transaction buyTransaction = new Transaction(
                transactionId, 
                userId, 
                fundCode, 
                "BUY", 
                amount, 
                transactionDate);
        
        // Registrar la transacción
        transactions.add(buyTransaction);
        
        // Actualizar el balance del fondo
        if (fundBalances.containsKey(fundCode)) {
            double currentBalance = fundBalances.get(fundCode);
            fundBalances.put(fundCode, currentBalance + amount);
        } else {
            fundBalances.put(fundCode, amount);
        }
        
        // Registrar la transacción en la base de datos
        saveTransactionToDatabase(buyTransaction);
        
        // Notificar al usuario
        sendNotificationToUser("Se ha realizado una compra por $" + amount + " en el fondo " + fundCode);
        
        System.out.println("Transacción de compra procesada exitosamente. ID: " + transactionId);
    }
    
    public void processSellTransaction(String fundCode, double amount) {
        // Validaciones para transacción SELL
        if (fundCode == null || fundCode.isEmpty()) {
            throw new IllegalArgumentException("El código del fondo no puede estar vacío");
        }
        
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        
        // Verificar si el fondo existe
        if (!isFundValid(fundCode)) {
            throw new IllegalArgumentException("El fondo no existe: " + fundCode);
        }
        
        // Verificar fondos suficientes
        Double currentBalance = fundBalances.getOrDefault(fundCode, 0.0);
        if (currentBalance < amount) {
            throw new IllegalArgumentException("Saldo insuficiente. Balance actual: " + currentBalance);
        }
        
        // Crear la transacción SELL
        String transactionId = UUID.randomUUID().toString();
        Date transactionDate = new Date();
        Transaction sellTransaction = new Transaction(
                transactionId, 
                userId, 
                fundCode, 
                "SELL", 
                amount, 
                transactionDate);
        
        // Registrar la transacción
        transactions.add(sellTransaction);
        
        // Actualizar el balance del fondo
        fundBalances.put(fundCode, currentBalance - amount);
        
        // Registrar la transacción en la base de datos
        saveTransactionToDatabase(sellTransaction);
        
        // Notificar al usuario
        sendNotificationToUser("Se ha realizado un rescate por $" + amount + " del fondo " + fundCode);
        
        System.out.println("Transacción de venta procesada exitosamente. ID: " + transactionId);
    }
    
    private boolean isFundValid(String fundCode) {
        // Simulación de validación de fondo
        return fundCode.startsWith("FUND");
    }
    
    private void saveTransactionToDatabase(Transaction transaction) {
        // Simulación de guardado en base de datos
        System.out.println("Guardando transacción en la base de datos: " + transaction.getId());
    }
    
    private void sendNotificationToUser(String message) {
        // Simulación de envío de notificación
        System.out.println("Notificación para usuario " + userId + ": " + message);
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public Map<String, Double> getFundBalances() {
        return fundBalances;
    }
    
}
