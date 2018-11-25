package com.mojang.minecraft.sound;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

// TODO.
public final class SoundManager {

   private SoundReader reader = new SoundReader();
   public Map sounds = new HashMap();
   private Map music = new HashMap();
   public Random random = new Random();
   public long lastMusic = System.currentTimeMillis() + 60000L;


   public final AudioInfo getAudioInfo(String var1, float var2, float var3) {
      List var4 = null;
      Map var5 = this.sounds;
      synchronized(this.sounds) {
         var4 = (List)this.sounds.get(var1);
      }

      if(var4 == null) {
         return null;
      } else {
         SoundData var7 = (SoundData)var4.get(this.random.nextInt(var4.size()));
         return new SoundInfo(var7, var3, var2);
      }
   }

   public void registerSound(File file, String name) {
      try {
         for(name = name.substring(0, name.length() - 4).replaceAll("/", "."); Character.isDigit(name.charAt(name.length() - 1)); name = name.substring(0, name.length() - 1)) {
            ;
         }
         SoundData data = SoundReader.read(file.toURI().toURL());
         synchronized(this.sounds) {
            List list;
            if((list = (List)this.sounds.get(name)) == null) {
               list = new ArrayList();
               this.sounds.put(name, list);
            }
            ((List)list).add(data);
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public final void registerMusic(String var1, File var2) {
      Map var3 = this.music;
      synchronized(this.music) {
         for(var1 = var1.substring(0, var1.length() - 4).replaceAll("/", "."); Character.isDigit(var1.charAt(var1.length() - 1)); var1 = var1.substring(0, var1.length() - 1)) {
            ;
         }

         Object var4;
         if((var4 = (List)this.music.get(var1)) == null) {
            var4 = new ArrayList();
            this.music.put(var1, var4);
         }

         ((List)var4).add(var2);
      }
   }

   public boolean playMusic(SoundPlayer var1, String var2) {
      List var3 = null;
      Map var4 = this.music;
      synchronized(this.music) {
         var3 = (List)this.music.get(var2);
      }

      if(var3 == null) {
         return false;
      } else {
         File var8 = (File)var3.get(this.random.nextInt(var3.size()));

         try {
            var1.play(new Music(var1, var8.toURI().toURL()));
         } catch (IOException var6) {
            var6.printStackTrace();
         }

         return true;
      }
   }
}
