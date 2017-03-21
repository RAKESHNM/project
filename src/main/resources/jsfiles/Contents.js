
$(document).ready(function(){
$('#first').append(" package com.razorthink.countFiles import java.io.BufferedReader;

                    import java.io.FileReader;
                    import java.io.IOException;

                    /**
                     * Created by antolivish on 25/2/17.
                     */
                    public class ReadFile {
                        public String readFile(String file) throws IOException {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String         line = null;
                            StringBuilder  stringBuilder = new StringBuilder();
                            String         ls = System.getProperty("line.separator");

                            try {
                                while((line = reader.readLine()) != null) {
                                    stringBuilder.append(line);
                                    stringBuilder.append(ls);
                                }

                                return stringBuilder.toString();
                            } finally {
                                reader.close();
                            }
                        }
                    }");
$('#second').append("alskddADAs");
//$('<p>Text</p>').appendTo('#first');
});