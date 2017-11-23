<?php 

// création de la chaine à ajouter dans le fichier
$chaine = "<br />- ";
$chaine .= "<a href='javascript:recherche(\"" . get_client_ip() . "\");'>" . get_client_ip() . "</a>";
$chaine .=  " - " . $_GET['phrase'];
function get_client_ip() {
    $ipaddress = '';
    if (getenv('HTTP_CLIENT_IP'))
        $ipaddress = getenv('HTTP_CLIENT_IP');
    else if(getenv('HTTP_X_FORWARDED_FOR'))
        $ipaddress = getenv('HTTP_X_FORWARDED_FOR');
    else if(getenv('HTTP_X_FORWARDED'))
        $ipaddress = getenv('HTTP_X_FORWARDED');
    else if(getenv('HTTP_FORWARDED_FOR'))
        $ipaddress = getenv('HTTP_FORWARDED_FOR');
    else if(getenv('HTTP_FORWARDED'))
       $ipaddress = getenv('HTTP_FORWARDED');
    else if(getenv('REMOTE_ADDR'))
        $ipaddress = getenv('REMOTE_ADDR');
    else
        $ipaddress = 'UNKNOWN';
    return $ipaddress;
};

$fp = fopen("texte.html","a");

fwrite($fp, $chaine);
fclose($fp);
echo "Ecriture reussie";

?>
