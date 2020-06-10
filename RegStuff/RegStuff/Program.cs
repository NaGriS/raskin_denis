using Microsoft.Win32;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;

namespace RegStuff
{
    class Program
    {
        // Example set setting
        public static void SetSettingValue()
        {
            RegistryKey folderKey = Registry.CurrentUser.CreateSubKey(@"Software\NewBanhammerSettings");

            folderKey.SetValue("Setting1", "Value123");
            folderKey.SetValue("Setting2", "Value321");

            folderKey.Close();
        }

        // Example read setting
        public static string ReadSettingValue(string SettingToGetValue)
        {
            RegistryKey folderKey = Registry.CurrentUser.OpenSubKey(@"Software\NewBanhammerSettings");
            
            var returnedSetting = folderKey.GetValue("Setting1").ToString();
            folderKey.Close();

            return returnedSetting;
        }

        // Example reading list of settings values
        public static void ReadSettingsValues(ref List<Setting> settings)
        {
          foreach(var setting in settings)
            {
                var keyName = Registry.CurrentUser.OpenSubKey(setting.KeyName);

                if (keyName != null)
                {
                    setting.KeyValue = keyName.GetValue(setting.KeySetting).ToString();
                    keyName.Close();
                }
            }
        }

        // Example setting list of settings values
        public static void SetSettingsValues(List<Setting> settings)
        {
            foreach (var setting in settings)
            {
                var keyName = Registry.CurrentUser.OpenSubKey(setting.KeyName, true);

                if (keyName != null)
                {
                    keyName.SetValue(setting.KeySetting, setting.KeyValue);
                    keyName.Close();
                }
            }
        }

        // Example setting list of settings values
        public static void WriteSettingsToFile(List<Setting> settings)
        {
            var fileName = @"E:\=Study\Bahnammer.txt";

            var settingsList = new SettingsList(settings);

            var settingAsJson = JsonConvert.SerializeObject(settingsList);
            File.WriteAllText(fileName, settingAsJson);
        }

        public static SettingsList ReadFromFile()
        {
            var fileName = @"E:\=Study\Bahnammer.txt";

            var settingsAsJson = File.ReadAllText(fileName);

            return JsonConvert.DeserializeObject<SettingsList>(settingsAsJson);
        }

        static void Main(string[] args)
        {

            // Example set value to Registry
            //SetSettingValue();

            // Read settings from Registry
            var settingsList = new List<Setting>();

            var keyName1 = @"Software\NewBanhammerSettings";
            settingsList.Add(new Setting(keyName1, "Setting1"));
            settingsList.Add(new Setting(keyName1, "Setting2"));
            // [(keyName1, Setting1, "", CurrentUser), (keyName1, Setting2, "", CurrentUser), ]
            ReadSettingsValues(ref settingsList);
            // [(keyName1, Setting1, "Value1", CurrentUser), (keyName1, Setting2, "Value2", CurrentUser)]

            // Write readed settings to file system
            WriteSettingsToFile(settingsList);

            // Read Registry settings from file system
            var readedSettings = ReadFromFile();

            // Set readed values to Registry
            SetSettingsValues(readedSettings.ListOfSettings);

            Console.ReadKey();
        }
    }
}
