package com.example.telegrambotjava.service;

import com.example.telegrambotjava.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    public TelegramBot(BotConfig config){
        this.config = config;
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) return;
        long chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        switch (messageText.trim()){
            case "/start":startCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                break;

            default:sendMessage(chatId,"idi nahuy");

        }
    }

    private void startCommandReceived(long id,String firstName){
        String answer = "Hi, " + firstName + ", what you want ?";
        sendMessage(id,answer);
    }
    private void sendMessage(long id,String text){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(id));
        message.setText(text);
        try{
            execute(message);
        }
        catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
